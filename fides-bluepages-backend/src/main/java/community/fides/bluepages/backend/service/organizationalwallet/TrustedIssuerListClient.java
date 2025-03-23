package community.fides.bluepages.backend.service.organizationalwallet;

import community.fides.bluepages.backend.service.organizationalwallet.dto.TrustedIssuerListOwDto;

import java.util.List;

public interface TrustedIssuerListClient {
    List<TrustedIssuerListOwDto> getTrustedIssuers();
}
