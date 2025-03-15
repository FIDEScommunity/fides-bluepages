package community.fides.bluepages.backend.service.organizationalwallet.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TrustedIssuerListOwDto {

    public String credentialType;
    private List<TrustedIssuerListDidOwDto> trustedDids = new ArrayList<>();

}
