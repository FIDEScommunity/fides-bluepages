package community.fides.bluepages.backend.service;

import community.fides.bluepages.backend.domain.ConfiguredType;
import community.fides.bluepages.backend.repository.ConfiguredTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfiguredTypeRetrieveService {

    private final ConfiguredTypeRepository configuredTypeRepository;


    @Transactional
    public ConfiguredType updateConfiguredType(final String credentialType, String issuanceUrl, String issuerConfigurationContent) {
        try {
            final var configuredType = configuredTypeRepository.findByCredentialType(credentialType).orElseGet(ConfiguredType::new);
            configuredType.setCredentialType(credentialType);
            configuredType.setIssuanceUrl(issuanceUrl);
            configuredType.setIssuanceContent(issuerConfigurationContent);
            return configuredTypeRepository.save(configuredType);
        } catch (Exception e) {
            log.error("Error updateConfiguredType credentialType {}, error: {}", credentialType, e.getMessage(), e);
            return null;
        }
    }

}
