package community.fides.bluepages.backend.rest.api.dto;

import community.fides.bluepages.backend.domain.CredentialStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CredentialDto {
    private String type;
    private CredentialStatus status;
    private List<ValidationPolicyResultDto> validationPolicyResults;
    private List<CredentialAttributeDto> attributes;
    private String issuerDid;
    private String subjectDid;
}
