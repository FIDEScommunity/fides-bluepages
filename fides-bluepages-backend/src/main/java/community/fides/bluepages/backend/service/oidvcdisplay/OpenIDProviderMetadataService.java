package community.fides.bluepages.backend.service.oidvcdisplay;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import community.fides.bluepages.backend.service.oidvcdisplay.dto.AttributeDefinition;
import community.fides.bluepages.backend.service.oidvcdisplay.dto.CredentialConfigurationSupported;
import community.fides.bluepages.backend.service.oidvcdisplay.dto.CredentialDefinition;
import community.fides.bluepages.backend.service.oidvcdisplay.dto.DisplayProperties;
import community.fides.bluepages.backend.service.oidvcdisplay.dto.OpenIDProviderMetadata;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import static community.fides.bluepages.backend.service.oidvcdisplay.LocaleDisplayFilter.getByLocale;


@Service
@RequiredArgsConstructor
public class OpenIDProviderMetadataService {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public OpenIDProviderMetadata getOpenIDProviderMetadata(final String issuanceContent) {
        if (issuanceContent == null) {
            return null;
        }
        return objectMapper.readValue(issuanceContent, OpenIDProviderMetadata.class);
    }

    public Optional<DisplayProperties> getIssuerDisplayPropertiesByLocale(OpenIDProviderMetadata openIDProviderMetadata, String locale) {
        if (openIDProviderMetadata.getDisplay() == null) {
            final DisplayProperties displayProperties = new DisplayProperties();
            displayProperties.setName(getHostname(openIDProviderMetadata.getCredentialIssuer()));
            return Optional.of(displayProperties);
        }
        return Optional.ofNullable(getByLocale(openIDProviderMetadata.getDisplay(), locale));
    }

    public Optional<DisplayProperties> getCredentialTypeDisplayPropertiesByLocaleForCredentialDefinitionType(String issuanceContent, String credentialDefinitionType, String locale) {
        final OpenIDProviderMetadata openIDProviderMetadata = getOpenIDProviderMetadata(issuanceContent);
        final Optional<String> credentialConfigurationId = getCredentialConfigurationIdForType(credentialDefinitionType, openIDProviderMetadata);
        if (credentialConfigurationId.isEmpty()) {
            return Optional.empty();
        }
        return getCredentialTypeDisplayPropertiesByLocale(getOpenIDProviderMetadata(issuanceContent), credentialConfigurationId.get(), locale);
    }

    public Optional<DisplayProperties> getCredentialTypeDisplayPropertiesByLocale(OpenIDProviderMetadata openIDProviderMetadata, String credentialConfigurationId, String locale) {
        if (!openIDProviderMetadata.getCredentialConfigurationsSupported().containsKey(credentialConfigurationId)) {
            return Optional.empty();
        }
        return Optional.of(getByLocale(openIDProviderMetadata.getCredentialConfigurationsSupported().get(credentialConfigurationId).getDisplay(), locale));
    }

    public Map<String, DisplayProperties> getCredentialSubjectDisplayPropertiesByLocale(String issuanceContent, String credentialConfigurationId, String locale) {
        return getCredentialSubjectDisplayPropertiesByLocale(getOpenIDProviderMetadata(issuanceContent), credentialConfigurationId, locale);
    }

    public Map<String, DisplayProperties> getCredentialSubjectDisplayPropertiesByLocale(OpenIDProviderMetadata openIDProviderMetadata, String credentialConfigurationId, String locale) {
        if (!openIDProviderMetadata.getCredentialConfigurationsSupported().containsKey(credentialConfigurationId)) {
            return Map.of();
        }

        var attributeDefinitions = getAttributeDefinitions(openIDProviderMetadata.getCredentialConfigurationsSupported().get(credentialConfigurationId));
        if (attributeDefinitions == null) {
            return Map.of();
        }
        return attributeDefinitions.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), getByLocale(entry.getValue().getDisplay(), locale)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, DisplayProperties> getCredentialSubjectDisplayPropertiesByLocaleForCredentialDefinitionType(String issuanceContent, String credentialDefinitionType, String locale) {
        final OpenIDProviderMetadata openIDProviderMetadata = getOpenIDProviderMetadata(issuanceContent);
        final Optional<String> credentialConfigurationId = getCredentialConfigurationIdForType(credentialDefinitionType, openIDProviderMetadata);
        if (credentialConfigurationId.isEmpty()) {
            return Collections.emptyMap();
        }

        return getCredentialSubjectDisplayPropertiesByLocale(openIDProviderMetadata, credentialConfigurationId.get(), locale);
    }

    private Optional<String> getCredentialConfigurationIdForType(final String credentialDefinitionType, final OpenIDProviderMetadata openIDProviderMetadata) {
        final Optional<String> credentialConfigurationId = openIDProviderMetadata.getCredentialConfigurationsSupported().entrySet().stream()
                .filter(entry -> entry.getValue().getCredentialDefinition().getType().contains(credentialDefinitionType))
                .map(entry -> entry.getKey())
                .findFirst();
        return credentialConfigurationId;
    }

    private Map<String, AttributeDefinition> getAttributeDefinitions(CredentialConfigurationSupported credentialConfiguration) {
        if (credentialConfiguration.getFormat().equalsIgnoreCase("vc+sd-jwt")) {
            return getSdJwtAttributes(credentialConfiguration);
        } else if (credentialConfiguration.getFormat().equalsIgnoreCase("mso_mdoc")) {
            return getMDocAttributes(credentialConfiguration);
        } else {
            return getVcJwtAttributes(credentialConfiguration);
        }
    }

    private static Map<String, AttributeDefinition> getVcJwtAttributes(CredentialConfigurationSupported credentialConfiguration) {
        final CredentialDefinition credentialDefinition = credentialConfiguration.getCredentialDefinition();
        if (credentialDefinition == null) {
            return Map.of();
        }
        return credentialDefinition.getCredentialSubject();
    }

    private Map<String, AttributeDefinition> getSdJwtAttributes(CredentialConfigurationSupported credentialConfiguration) {
        if (credentialConfiguration.getClaims() == null) {
            return Map.of();
        }
        return objectMapper.convertValue(credentialConfiguration.getClaims(), new TypeReference<>() {});
    }

    private Map<String, AttributeDefinition> getMDocAttributes(CredentialConfigurationSupported credentialConfiguration) {
        if (credentialConfiguration.getClaims() == null || !credentialConfiguration.getClaims().fieldNames().hasNext()) {
            return Map.of();
        }
        return objectMapper.convertValue(credentialConfiguration.getClaims().get(credentialConfiguration.getClaims().fieldNames().next()), new TypeReference<>() {});
    }

    private static String getHostname(final String issuerUrl) {
        final int startIndex = issuerUrl.indexOf("://") + 3;
        final int endIndex = issuerUrl.indexOf("/", startIndex);
        if (endIndex < 0) {
            return issuerUrl.substring(startIndex);
        }
        return issuerUrl.substring(startIndex, endIndex);
    }
}
