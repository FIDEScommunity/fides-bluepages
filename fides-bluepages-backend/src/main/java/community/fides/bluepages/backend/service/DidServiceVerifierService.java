package community.fides.bluepages.backend.service;

import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.domain.DidService;
import community.fides.bluepages.backend.repository.DidServiceRepository;
import community.fides.bluepages.backend.service.organizationalwallet.VerifiablePresentationVerifier;
import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatus;
import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatusResponse;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DidServiceVerifierService {

    private final DidServiceRepository didServiceRepository;
    private final VerifiablePresentationVerifier verifiablePresentationVerifier;


    public VerifiablePresentationStatusResponse validate(String didExternalKey, String didServiceExternalKey) {
        final DidService didService = didServiceRepository.findByDid_ExternalKeyAndExternalKey(didExternalKey, didServiceExternalKey).orElseThrow(() -> new IllegalArgumentException("DidService not found"));

        final ResponseEntity<String> jwtResponse = new RestTemplate().getForEntity(didService.getServiceEndpoint(), String.class);
        if (!jwtResponse.getStatusCode().is2xxSuccessful() || jwtResponse.getBody() == null) {
            return VerifiablePresentationStatusResponse.builder()
                    .overallStatus(VerifiablePresentationStatus.UNKNOWN)
                    .build();
        }

        return verifiablePresentationVerifier.validate(jwtResponse.getBody());
    }

    public Map<DidService, VerifiablePresentationStatusResponse> validate(Did did) {
        return did.getServices().parallelStream()
                .filter(didService -> !didService.getCredentials().isEmpty())
                .map(didService -> Map.entry(didService, validate(didService)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


    }

    public VerifiablePresentationStatusResponse validate(final DidService didService) {
        final ResponseEntity<String> jwtResponse = new RestTemplate().getForEntity(didService.getServiceEndpoint(), String.class);
        if (!jwtResponse.getStatusCode().is2xxSuccessful() || jwtResponse.getBody() == null) {
            return null;
        }
        return verifiablePresentationVerifier.validate(jwtResponse.getBody());
    }

}
