package community.fides.bluepages.backend.service.crawler.documentfetcher;

import community.fides.bluepages.backend.domain.Did;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EbsiDocumentFetcher implements DidDocumentFetcher {

    private final RestTemplate restTemplate;

    public EbsiDocumentFetcher(@Qualifier("ebsi") final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public boolean canFetchRawData(final Did did) {
        if ((did == null) || (did.getDid() == null)) {
            return false;
        }
        return did.getDid().startsWith("did:ebsi:");
    }

    @Override
    public byte[] fetchRawData(final Did did) {
        return restTemplate.getForObject("/did-registry/v5/identifiers/" + did.getDid(), byte[].class);
    }
}
