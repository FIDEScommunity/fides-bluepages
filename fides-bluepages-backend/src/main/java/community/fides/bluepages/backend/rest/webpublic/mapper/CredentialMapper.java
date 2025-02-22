package community.fides.bluepages.backend.rest.webpublic.mapper;

import community.fides.bluepages.backend.configuration.DisplayConfig;
import community.fides.bluepages.backend.domain.Credential;
import community.fides.bluepages.backend.domain.CredentialStatus;
import community.fides.bluepages.backend.rest.webpublic.dto.CredentialDto;
import community.fides.bluepages.backend.rest.webpublic.dto.ValidationPolicyResultDto;
import community.fides.bluepages.backend.service.ConfiguredTypesDisplayPropertiesCache;
import community.fides.bluepages.backend.service.oidvcdisplay.dto.DisplayProperties;
import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatus;
import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatusResponse;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CredentialMapper {

    private final CredentialAttributeMapper credentialAttributeMapper;
    private final ConfiguredTypesDisplayPropertiesCache configuredTypesDisplayPropertiesCache;
    private final DisplayConfig displayConfig;

    public CredentialDto from(Credential credential, String locale, final VerifiablePresentationStatusResponse validationResults) {
        if (credential == null) {
            return null;
        }
        final DisplayProperties typeDisplayProperties = configuredTypesDisplayPropertiesCache.getCredentialDisplayPropertiesByLocale(locale).get(credential.getType());
        var credentialSubjectDisplayPropertiesByLocale = configuredTypesDisplayPropertiesCache.getCredentialSubjectDisplayPropertiesByLocale(locale);
        var attributesDisplayProperties = credentialSubjectDisplayPropertiesByLocale.get(credential.getType());

        return CredentialDto.builder()
                .id(credential.getId())
                .icon(displayConfig.getCredentialIcon(credential.getType()))
                .type(credential.getType())
                .displayName((typeDisplayProperties == null) ? credential.getType() : typeDisplayProperties.getName())
                .status(getStatus(credential, validationResults))
                .validationPolicyResults(getValidationPolicyResults(credential, validationResults))
                .lastUpdated(credential.getLastUpdated())
                .attributes(credentialAttributeMapper.from(
                        displayConfig.getCredentials().get(credential.getType()), credential.getAttributes(), attributesDisplayProperties)
                )
                .issuerDidId(credential.getIssuerDid())
                .subjectDid(credential.getSubjectDid())
                .build();
    }

    private List<ValidationPolicyResultDto> getValidationPolicyResults(final Credential credential, final VerifiablePresentationStatusResponse validationResults) {
        if ((validationResults == null) || !validationResults.getStatusPerCredential().containsKey(credential.getType())) {
            return Collections.emptyList();
        }
        return validationResults.getStatusPerCredential().get(credential.getType()).getPolicyResults().stream()
                .map(policyResultResponse -> ValidationPolicyResultDto.builder()
                        .policyName(policyResultResponse.getPolicyName())
                        .policyDescription(policyResultResponse.getPolicyDescription())
                        .isValid(policyResultResponse.isValid())
                        .build()
                ).toList();
    }

    private CredentialStatus getStatus(final Credential credential, final VerifiablePresentationStatusResponse validationResults) {
        if ((validationResults == null) || !validationResults.getStatusPerCredential().containsKey(credential.getType())) {
            return CredentialStatus.UNCHECKED;
        }
        return from(validationResults.getStatusPerCredential().get(credential.getType()).getVerifiablePresentationStatus());
    }

    private CredentialStatus from(final VerifiablePresentationStatus verifiablePresentationStatus) {
        if (verifiablePresentationStatus == null) {
            return CredentialStatus.UNCHECKED;
        }
        return switch (verifiablePresentationStatus) {
            case VALID -> CredentialStatus.VALID;
            case INVALID -> CredentialStatus.INVALID;
            case EXPIRED -> CredentialStatus.EXPIRED;
            case UNKNOWN -> CredentialStatus.UNCHECKED;
        };
    }

    public List<CredentialDto> from(List<Credential> credentials, String locale, final VerifiablePresentationStatusResponse validationResults) {
        return credentials.stream().map(credential -> from(credential, locale, validationResults)).toList();
    }

}
