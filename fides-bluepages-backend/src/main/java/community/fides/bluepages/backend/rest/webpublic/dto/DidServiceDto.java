package community.fides.bluepages.backend.rest.webpublic.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DidServiceDto {

    private Long id;
    private String externalKey;
    private String title;
    private String icon;
    private String serviceId;
    private String serviceTypeLabel;
    private String serviceType;
    private String serviceTypeJson;
    private String serviceEndpointLabel;
    private String serviceEndpoint;
    private String serviceEndpointJson;
    private List<CredentialDto> credentials;

}
