package community.fides.bluepages.backend.service.organizationalwallet;

import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatusResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Profile("!local")
@Service
public class WalletVerifiablePresentationVerifier implements VerifiablePresentationVerifier {

    private final RestTemplate organizationalWalletRestTemplate;

    public WalletVerifiablePresentationVerifier(@Qualifier("organizationalWalletClient") final RestTemplate organizationalWalletRestTemplate) {
        this.organizationalWalletRestTemplate = organizationalWalletRestTemplate;
    }


    public VerifiablePresentationStatusResponse validate(final String presentationJwt) {
        var body = Map.of("vpTokenJwt", presentationJwt);
        return organizationalWalletRestTemplate.postForEntity("/verifiablepresentation/verify", body, VerifiablePresentationStatusResponse.class).getBody();
    }

}
