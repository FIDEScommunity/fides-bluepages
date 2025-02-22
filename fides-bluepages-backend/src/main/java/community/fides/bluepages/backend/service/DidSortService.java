package community.fides.bluepages.backend.service;

import community.fides.bluepages.backend.configuration.DisplayConfig;
import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.domain.DidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DidSortService {

    private final DisplayConfig displayConfig;

    public Did sortDid(Did did) {
        did.getServices().sort((o1, o2) -> getSortOrder(o1).compareTo(getSortOrder(o2)));
        return did;
    }

    private Integer getSortOrder(final DidService didService) {
        if ((didService.getCredentials() == null) || didService.getCredentials().isEmpty()) {
            return displayConfig.getServiceDisplayOrder(didService.getServiceType());
        } else {
            return displayConfig.getCredentialDisplayOrder(didService.getCredentials().getFirst().getType());
        }
    }

}
