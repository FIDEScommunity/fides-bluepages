package community.fides.bluepages.backend.service.crawler.credentialextractor;

import community.fides.bluepages.backend.domain.Credential;
import community.fides.bluepages.backend.domain.DidService;
import java.util.List;

public interface CredentialExtractor {

    List<Credential> extractCredentials(DidService didService);
}
