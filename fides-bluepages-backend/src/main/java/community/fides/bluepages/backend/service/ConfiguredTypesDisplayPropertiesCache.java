package community.fides.bluepages.backend.service;

import community.fides.bluepages.backend.repository.ConfiguredTypeRepository;
import community.fides.bluepages.backend.service.oidvcdisplay.OpenIDProviderMetadataService;
import community.fides.bluepages.backend.service.oidvcdisplay.dto.DisplayProperties;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfiguredTypesDisplayPropertiesCache {

    private final ConfiguredTypeRepository configuredTypeRepository;
    private final OpenIDProviderMetadataService openIDProviderMetadataService;

    /**
     * Returns a map with per CredentialType the DisplayProperties for the locale per attribute
     */
    @Cacheable("configured-types-subject")
    public Map<String, Map<String, DisplayProperties>> getCredentialSubjectDisplayPropertiesByLocale(String locale) {
        return configuredTypeRepository.findAll().parallelStream()
                .map(configuredType -> Map.entry(configuredType.getCredentialType(),
                                                 openIDProviderMetadataService.getCredentialSubjectDisplayPropertiesByLocaleForCredentialDefinitionType(configuredType.getIssuanceContent(), configuredType.getCredentialType(), locale)))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    /**
     * Returns a map with per CredentialType the DisplayProperties of the CredentialType itself for the locale
     */
    @Cacheable("configured-types-credential")
    public Map<String, DisplayProperties> getCredentialDisplayPropertiesByLocale(String locale) {
        return configuredTypeRepository.findAll().parallelStream()
                .map(configuredType -> Map.entry(configuredType.getCredentialType(),
                                                 openIDProviderMetadataService.getCredentialTypeDisplayPropertiesByLocaleForCredentialDefinitionType(configuredType.getIssuanceContent(), configuredType.getCredentialType(), locale)))
                .filter(entry -> entry.getValue().isPresent())
                .collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().get()));
    }


    @Scheduled(fixedDelayString = "PT15M")
    @CacheEvict({"configured-types-subject", "configured-types-credential"})
    public void evictCache() {
        log.debug("Evicted configured-types cache");
    }


}
