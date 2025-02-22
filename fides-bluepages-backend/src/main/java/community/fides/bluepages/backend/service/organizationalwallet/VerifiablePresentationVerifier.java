package community.fides.bluepages.backend.service.organizationalwallet;

import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatusResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VerifiablePresentationVerifier {

    private final RestTemplate organizationalWalletRestTemplate;

    public VerifiablePresentationVerifier(@Qualifier("organizationalWalletClient") final RestTemplate organizationalWalletRestTemplate) {
        this.organizationalWalletRestTemplate = organizationalWalletRestTemplate;
    }


    public VerifiablePresentationStatusResponse validate(final String presentationJwt) {
        var body = Map.of("vpTokenJwt", presentationJwt);
        return organizationalWalletRestTemplate.postForEntity("/verifiablepresentation/verify", body, VerifiablePresentationStatusResponse.class).getBody();
    }

}
