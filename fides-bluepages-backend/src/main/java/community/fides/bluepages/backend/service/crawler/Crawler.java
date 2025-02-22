package community.fides.bluepages.backend.service.crawler;


import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.service.ConfiguredTypeService;
import community.fides.bluepages.backend.service.crawler.documentfetcher.DidDocumentFetcher;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Crawler {

    private final List<DidDocumentFetcher> didDocumentFetchers;
    private final DidDocumentBuilder didDocumentBuilder;
    private final ConfiguredTypeService configuredTypeService;

    @SneakyThrows
    public Optional<Did> crawlDidDocument(final String sDid) {
        Did did = Did.builder().did(sDid).build();
        final Optional<DidDocumentFetcher> didDocumentFetcher = findDidDocumentFetcher(did);
        if (didDocumentFetcher.isEmpty()) {return Optional.empty();}

        final DidDocumentFetcher fetcher = didDocumentFetcher.get();
        try {
            final byte[] rawData = fetcher.fetchRawData(did);
            final Did didDocument = didDocumentBuilder.build(did, rawData);
            final Did updatedDid = configuredTypeService.removeNotAllowedTypes(didDocument);
            updatedDid.setMeetsTypeRequirements(configuredTypeService.containsAtLeastOneRequiredServiceOrCredential(didDocument));
            return Optional.of(updatedDid);
        } catch (Exception e) {
            log.error("Error crawling {}, error: {}", did, e.getMessage(), e);
            return Optional.empty();
        }
    }


    private Optional<DidDocumentFetcher> findDidDocumentFetcher(Did did) {
        return didDocumentFetchers.stream().filter(fetcher -> fetcher.canFetchRawData(did)).findFirst();
    }


}
