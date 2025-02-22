package community.fides.bluepages.backend.service.crawler;

import community.fides.bluepages.backend.configuration.CrawlerConfig;
import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.domain.ExclusiveLock;
import community.fides.bluepages.backend.repository.DidRepository;
import community.fides.bluepages.backend.service.DidDocumentService;
import community.fides.bluepages.backend.service.DidStoreService;
import community.fides.bluepages.backend.service.ExclusiveLockService;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
@Slf4j
public class CrawlerScheduler {

    private final Crawler crawler;
    private final DidRepository didRepository;
    private final DidStoreService didStoreService;
    private final CrawlerConfig crawlerConfig;
    private final ExclusiveLockService exclusiveLockService;
    private final DidDocumentService didDocumentService;

    @Scheduled(fixedDelay = 1000 * 60)
    public void crawl() {
        if (!crawlerConfig.isEnabled()) {return;}

        IntStream.range(0, crawlerConfig.getMaxNumberOfConcurrentCrawls())
                .forEach(this::crawlPartition);
    }

    private void crawlPartition(final int partition) {
        final LocalDateTime recentlyChangedPointInTime = LocalDateTime.now().minus(crawlerConfig.getRecentlyChangedDuration());
        final LocalDateTime recentlyChangedCrawlTimestamp = LocalDateTime.now().minus(crawlerConfig.getCrawlFrequencyRecentlyUpdatedDids());
        final LocalDateTime nonRecentlyChangedCrawlTimestamp = LocalDateTime.now().minus(crawlerConfig.getCrawlFrequencyNotRecentlyUpdatedDids());

        final Optional<ExclusiveLock> exclusiveLock = exclusiveLockService.obtainLock("CRAWL_LOCK" + partition);
        try {
            if (exclusiveLock.isEmpty()) {return;}
            didRepository.findDidsToCrawl(partition, crawlerConfig.getMaxNumberOfConcurrentCrawls(), recentlyChangedPointInTime, recentlyChangedCrawlTimestamp, nonRecentlyChangedCrawlTimestamp)
                    .stream().map(didRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(did -> {
                        log.info("Processing didId {}, rawDataLastUpdatedAt: {}, lastCrawledAt {}", did.getId(), did.getRawDataLastUpdatedAt(), did.getLastCrawledAt());
                        final Optional<Did> crawledDidDocument = crawler.crawlDidDocument(did.getDid());
                        if (crawledDidDocument.isPresent()) {
                            didStoreService.updateTxNew(did.getId(), crawledDidDocument.get());
                            addIssuerDids(crawledDidDocument.get());
                        } else {
                            didStoreService.updateLastCrawledAt(did.getId(), LocalDateTime.now()); // Retrieving did failed. Try again later.
                        }
                        exclusiveLockService.renewLock(exclusiveLock.get());
                    });
        } finally {
            exclusiveLockService.releaseLock(exclusiveLock);
        }
    }

    private void addIssuerDids(final Did did) {
        did.getServices().stream()
                .flatMap(didService -> didService.getCredentials().stream())
                .map(credential -> credential.getIssuerDid())
                .filter(sDid -> !ObjectUtils.isEmpty(sDid))
                .distinct()
                .forEach(didDocumentService::addWhenNotExists);

    }


}
