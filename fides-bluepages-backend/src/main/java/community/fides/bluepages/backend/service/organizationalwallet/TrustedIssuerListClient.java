package community.fides.bluepages.backend.service.organizationalwallet;

import community.fides.bluepages.backend.service.organizationalwallet.dto.TrustedIssuerListDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TrustedIssuerListClient {

    private final RestTemplate organizationalWalletRestTemplate;

    public TrustedIssuerListClient(@Qualifier("organizationalWalletClient") final RestTemplate organizationalWalletRestTemplate) {
        this.organizationalWalletRestTemplate = organizationalWalletRestTemplate;
    }


    public List<TrustedIssuerListDto> getTrustedIssuers() {
        final TrustedIssuerListDto[] response = organizationalWalletRestTemplate.getForEntity("/trustedissuerlist", TrustedIssuerListDto[].class).getBody();
        return (response == null) ? List.of() : List.of(response);
    }
}
