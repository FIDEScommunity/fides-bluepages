package community.fides.bluepages.backend.service.crawler.ebsiapicrawler;

import community.fides.bluepages.backend.repository.DidRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class EbsiDidApiDidProcessor {

    private final RestTemplate restTemplate;
    private final DidRepository didRepository;

    public EbsiDidApiDidProcessor(@Qualifier("ebsi") final RestTemplate restTemplate, final DidRepository didRepository) {
        this.restTemplate = restTemplate;
        this.didRepository = didRepository;
    }


    @Transactional
    public Integer retrieveAndStoreDidDocuments(int page, int pageSize) {
        final DidApiResult didApiResult = restTemplate.getForObject("/did-registry/v5/identifiers?page[after]=" + page + "&page[size]=" + pageSize, DidApiResult.class);
        if (didApiResult == null) {return 0;}
        if (didApiResult.items == null) {return 0;}
        if (didApiResult.items.isEmpty()) {return 0;}
        didApiResult.items.forEach(didResult -> {
            didRepository.addWhenNotExists(UUID.randomUUID().toString(), didResult.did, LocalDateTime.now());
        });
        log.info("Processed {} DIDs from EBSI api", didApiResult.items.size());
        return didApiResult.items.size();
    }

    private record DidApiResult(List<DidResult> items) {}

    private record DidResult(String did) {}


}
