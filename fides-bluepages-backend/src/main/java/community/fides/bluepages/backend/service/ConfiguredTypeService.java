package community.fides.bluepages.backend.service;

import community.fides.bluepages.backend.configuration.DisplayConfig;
import community.fides.bluepages.backend.domain.Credential;
import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.domain.DidService;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfiguredTypeService {

    private final DisplayConfig displayConfig;

    public boolean isAllowedCredential(Credential credential) {
        return displayConfig.getCredentials().containsKey(credential.getType());
    }

    public boolean isAllowedService(DidService service) {
        if (service.isLinkedVerifiablePresentation()) {
            return true;
        }
        return displayConfig.getServices().containsKey(service.getServiceType());
    }

    public boolean containsAtLeastOneRequiredServiceOrCredential(Did did) {
        if (did.getServices() == null || did.getServices().isEmpty()) {
            return false;
        }
        return did.getServices().stream()
                .filter(this::isAllowedService)
                .flatMap(service -> service.getCredentials().stream())
                .anyMatch(this::isAllowedCredential);
    }

    public Did removeNotAllowedTypes(Did did) {
        did.setServices(new ArrayList<>(did.getServices()));
        did.getServices().removeIf(service -> !isAllowedService(service));
        did.getServices().forEach(service -> {
            service.setCredentials(new ArrayList<>(service.getCredentials()));
            service.getCredentials().removeIf(credential -> !isAllowedCredential(credential));
        });
        did.getServices().removeIf(service -> service.isLinkedVerifiablePresentation() && service.getCredentials().isEmpty());
        return did;
    }

}
