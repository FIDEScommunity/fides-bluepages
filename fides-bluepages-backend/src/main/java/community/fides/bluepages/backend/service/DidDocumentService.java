package community.fides.bluepages.backend.service;

import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.domain.DidService;
import community.fides.bluepages.backend.repository.DidRepository;
import community.fides.bluepages.backend.rest.api.dto.DidDto;
import community.fides.bluepages.backend.rest.api.mapper.RestDidMapper;
import community.fides.bluepages.backend.service.crawler.Crawler;
import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatusResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DidDocumentService {

    private final DidRepository didRepository;
    private final Crawler crawler;
    private final DidStoreService didStoreService;
    private final DidServiceVerifierService didServiceVerifierService;
    private final RestDidMapper didMapper;

    public Page<Did> searchDids(final String searchText, final Pageable pageable) {
        var allSpecification = new ArrayList<Specification<Did>>();
        allSpecification.add(didRepository.containsDid(searchText));
        if (!searchText.isEmpty()) {
            addAll(allSpecification, searchText, didRepository::containsDid);
            addAll(allSpecification, searchText, didRepository::containsCredentialType);
            addAll(allSpecification, searchText, didRepository::containsCredentialAttributeValue);
            addAll(allSpecification, searchText, didRepository::containsCredentialAttributeKey);
            addAll(allSpecification, searchText, didRepository::containsServiceId);
            addAll(allSpecification, searchText, didRepository::containsServiceType);
            addAll(allSpecification, searchText, didRepository::containsServiceTypeJson);
            addAll(allSpecification, searchText, didRepository::containsServiceEndpoint);
            addAll(allSpecification, searchText, didRepository::containsServiceEndpointJson);
        }
        final Specification<Did> orSpecification = didRepository.allOfOr(allSpecification);
        final Specification<Did> specification = orSpecification.and(didRepository.meetsTypeRequirements(true));
        return didRepository.findAll(specification, pageable);
    }

    public DidDto getDidAndValidate(final String didId, final String locale) {
        final var didFromDb = didRepository.findDidByDid(didId).orElseThrow(() -> new IllegalArgumentException("Did not found"));
        final var crawledDidDocument = crawler.crawlDidDocument(didFromDb.getDid()).orElseThrow(() -> new IllegalArgumentException("Did not found"));
        final Did updatedDid = didStoreService.update(didFromDb.getId(), crawledDidDocument);
        final Map<DidService, VerifiablePresentationStatusResponse> validationResults = didServiceVerifierService.validate(updatedDid);

        return didMapper.from(updatedDid, locale, validationResults);
    }

    @Transactional
    public void addWhenNotExists(String did) {
        didRepository.addWhenNotExists(UUID.randomUUID().toString(), did, LocalDateTime.now());
    }

    private void addAll(List<Specification<Did>> allSpecification, String searchText, Function<String, Specification<Did>> specificationFunction) {
        allSpecification.addAll(
                Arrays.stream(searchText.split(" "))
                        .map(specificationFunction)
                        .toList()
        );
    }


}
