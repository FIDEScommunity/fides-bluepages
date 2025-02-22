package community.fides.bluepages.backend.service.crawler.ebsiapicrawler;

import community.fides.bluepages.backend.configuration.CrawlerConfig;
import community.fides.bluepages.backend.domain.ExclusiveLock;
import community.fides.bluepages.backend.service.ExclusiveLockService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EbsiDidApiCrawlerScheduler {

    private final int PAGE_SIZE = 50;
    private final ExclusiveLockService exclusiveLockService;
    private final CrawlerConfig crawlerConfig;
    private final EbsiDidApiDidProcessor ebsiDidApiDidProcessor;

    //    @Scheduled(fixedDelayString = "PT6H", initialDelayString = "PT1M")
    @Scheduled(fixedDelayString = "PT6H")
    public void crawl() {
        if (!crawlerConfig.isEbsiApiCrawlerEnabled()) {return;}

        final Optional<ExclusiveLock> exclusiveLock = exclusiveLockService.obtainLock("EBSI_LIST_API_CRAWL_LOCK");
        try {
            if (exclusiveLock.isEmpty()) {return;}
            int page = 1;
            int rowsHandled = ebsiDidApiDidProcessor.retrieveAndStoreDidDocuments(1, PAGE_SIZE);
            while (rowsHandled == PAGE_SIZE) {
                rowsHandled = ebsiDidApiDidProcessor.retrieveAndStoreDidDocuments(page++, PAGE_SIZE);
                exclusiveLockService.renewLock(exclusiveLock.get());
            }
        } finally {
            exclusiveLockService.releaseLock(exclusiveLock);
        }
    }


}
