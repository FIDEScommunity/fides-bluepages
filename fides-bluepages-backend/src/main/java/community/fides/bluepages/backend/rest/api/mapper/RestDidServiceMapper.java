package community.fides.bluepages.backend.rest.api.mapper;

import community.fides.bluepages.backend.configuration.DisplayConfig;
import community.fides.bluepages.backend.domain.Credential;
import community.fides.bluepages.backend.domain.DidService;
import community.fides.bluepages.backend.rest.api.dto.DidServiceDto;
import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatusResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestDidServiceMapper {

    private final RestCredentialMapper credentialMapper;
    private final DisplayConfig displayConfig;

    public DidServiceDto from(DidService didService, String locale, final Map<DidService, VerifiablePresentationStatusResponse> validationResults) {
        return DidServiceDto.builder()
                .serviceId(didService.getServiceId())
                .serviceType(didService.getServiceType() != null ? didService.getServiceType() : didService.getServiceTypeJson())
                .serviceEndpoint(didService.getServiceEndpoint() != null ? didService.getServiceEndpoint() : didService.getServiceEndpointJson())
                .credentials(credentialMapper.from(getSortedCredentials(didService.getCredentials()), locale, validationResults.get(didService)))
                .build();
    }

    private List<Credential> getSortedCredentials(final List<Credential> credentials) {
        return credentials.stream()
                .sorted((o1, o2) -> displayConfig.getCredentialDisplayOrder(o1.getType()).compareTo(displayConfig.getCredentialDisplayOrder(o2.getType())))
                .toList();
    }

    public List<DidServiceDto> from(List<DidService> didServices, String locale, final Map<DidService, VerifiablePresentationStatusResponse> validationResults) {
        return didServices.stream().map(didService -> from(didService, locale, validationResults)).toList();
    }
}

