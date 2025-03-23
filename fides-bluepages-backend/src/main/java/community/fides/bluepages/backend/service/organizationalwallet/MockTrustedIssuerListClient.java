package community.fides.bluepages.backend.service.organizationalwallet;

import community.fides.bluepages.backend.configuration.MockOrganizationalWalletConfig;
import community.fides.bluepages.backend.service.organizationalwallet.dto.TrustedIssuerListOwDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("local")
@Component
@RequiredArgsConstructor
public class MockTrustedIssuerListClient implements TrustedIssuerListClient {

    private final MockOrganizationalWalletConfig mockOrganizationalWalletConfig;

    public List<TrustedIssuerListOwDto> getTrustedIssuers() {
        return mockOrganizationalWalletConfig.getTrustedIssuerList();
    }
}
