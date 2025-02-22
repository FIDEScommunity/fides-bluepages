package community.fides.bluepages.backend.service.organizationalwallet.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifiablePresentationStatusResponse {

    private VerifiablePresentationStatus overallStatus;
    private Map<String, VerifiablePresentationVerifyResult> statusPerCredential;


    @Getter
    @Setter
    @NoArgsConstructor
    public static class VerifiablePresentationVerifyResult {
        private VerifiablePresentationStatus verifiablePresentationStatus;
        private List<PolicyResultResponse> policyResults = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PolicyResultResponse {
        private String policyName;
        private String policyDescription;
        private boolean isValid;
    }
}
