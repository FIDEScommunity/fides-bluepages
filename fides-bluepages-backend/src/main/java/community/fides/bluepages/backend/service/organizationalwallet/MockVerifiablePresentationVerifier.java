package community.fides.bluepages.backend.service.organizationalwallet;

import community.fides.bluepages.backend.configuration.MockOrganizationalWalletConfig;
import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatus;
import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Profile("local")
@Service
@RequiredArgsConstructor
public class MockVerifiablePresentationVerifier implements VerifiablePresentationVerifier {

    private final MockOrganizationalWalletConfig mockOrganizationalWalletConfig;

    public VerifiablePresentationStatusResponse validate(final String presentationJwt) {
        return mockOrganizationalWalletConfig.getVerifiablePresentationStatus();
    }
}
