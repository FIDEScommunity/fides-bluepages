package community.fides.bluepages.backend.rest.webpublic.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TrustedIssuerListDto {

    public String credentialType;
    private List<DidDto> trustedDids = new ArrayList<>();

}
