package community.fides.bluepages.backend.service.organizationalwallet;

import community.fides.bluepages.backend.service.organizationalwallet.dto.TrustedIssuerListOwDto;
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


    public List<TrustedIssuerListOwDto> getTrustedIssuers() {
        final TrustedIssuerListOwDto[] response = organizationalWalletRestTemplate.getForEntity("/trustedissuerlist", TrustedIssuerListOwDto[].class).getBody();
        return (response == null) ? List.of() : List.of(response);
    }
}
