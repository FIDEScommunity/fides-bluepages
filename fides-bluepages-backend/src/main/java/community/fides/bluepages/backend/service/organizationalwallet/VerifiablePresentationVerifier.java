package community.fides.bluepages.backend.service.organizationalwallet;

import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatusResponse;

public interface VerifiablePresentationVerifier {
    VerifiablePresentationStatusResponse validate(final String presentationJwt);
}
