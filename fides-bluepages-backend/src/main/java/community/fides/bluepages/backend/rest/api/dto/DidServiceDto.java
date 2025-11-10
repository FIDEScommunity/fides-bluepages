package community.fides.bluepages.backend.rest.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DidServiceDto {
    private String serviceId;
    private String serviceType;
    private String serviceEndpoint;
    private List<CredentialDto> credentials;
}
