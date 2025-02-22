package community.fides.bluepages.backend.service.organizationalwallet.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TrustedIssuerListDto {

    public String credentialType;
    private List<TrustedIssuerListDidDto> trustedDids = new ArrayList<>();

}
