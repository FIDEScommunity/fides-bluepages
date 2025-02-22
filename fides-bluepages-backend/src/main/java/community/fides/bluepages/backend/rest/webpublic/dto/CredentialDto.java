package community.fides.bluepages.backend.rest.webpublic.dto;

import community.fides.bluepages.backend.domain.CredentialStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CredentialDto {

    private Long id;
    private String icon;
    private String type;
    private String displayName;
    private CredentialStatus status;
    private List<ValidationPolicyResultDto> validationPolicyResults;
    private LocalDateTime lastUpdated;
    private List<CredentialAttributeDto> attributes;
    private String issuerDidId;
    private String subjectDid;
    private DidDto issuerDid;


}
