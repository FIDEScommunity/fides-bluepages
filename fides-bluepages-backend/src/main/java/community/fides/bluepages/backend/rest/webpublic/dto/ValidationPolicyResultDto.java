package community.fides.bluepages.backend.rest.webpublic.dto;

import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatusResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidationPolicyResultDto {
    private String policyName;
    private String policyDescription;
    private boolean isValid;

    public static ValidationPolicyResultDto from(VerifiablePresentationStatusResponse.PolicyResultResponse policyResultResponse) {
        return ValidationPolicyResultDto.builder()
                .policyName(policyResultResponse.getPolicyName())
                .policyDescription(policyResultResponse.getPolicyDescription())
                .isValid(policyResultResponse.isValid())
                .build();
    }
}
