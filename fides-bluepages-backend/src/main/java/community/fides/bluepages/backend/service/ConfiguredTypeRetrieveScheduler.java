package community.fides.bluepages.backend.service;

import community.fides.bluepages.backend.configuration.DisplayConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfiguredTypeRetrieveScheduler {

    private final DisplayConfig displayConfig;
    private final ConfiguredTypeRetrieveService configuredTypeRetrieveService;

    @Scheduled(fixedDelayString = "PT24H")
    public void retrieveConfiguredTypeConfigurations() {
        displayConfig.getCredentials().forEach((key, value) -> {
            retrieveAndStoreTypeConfig(key, value.issuanceUrl());
        });
    }

    private void retrieveAndStoreTypeConfig(final String credentialType, final String issuanceUrl) {
        try {
            final RestTemplate restTemplate = new RestTemplate();
            final String configurationContent = restTemplate.getForObject(issuanceUrl, String.class);
            configuredTypeRetrieveService.updateConfiguredType(credentialType, issuanceUrl, configurationContent);
            log.info("Updated configuredType credentialType {}, url: {}", credentialType, issuanceUrl);
        } catch (Exception e) {
            log.error("Error updating configuredType credentialType {}, url: {}, error: {}", credentialType, issuanceUrl, e.getMessage(), e);
        }
    }
}
