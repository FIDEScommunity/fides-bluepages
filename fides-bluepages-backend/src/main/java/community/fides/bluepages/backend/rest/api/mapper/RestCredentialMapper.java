package community.fides.bluepages.backend.rest.api.mapper;

import community.fides.bluepages.backend.configuration.DisplayConfig;
import community.fides.bluepages.backend.domain.Credential;
import community.fides.bluepages.backend.domain.CredentialStatus;
import community.fides.bluepages.backend.rest.api.dto.CredentialDto;
import community.fides.bluepages.backend.rest.api.dto.ValidationPolicyResultDto;
import community.fides.bluepages.backend.service.ConfiguredTypesDisplayPropertiesCache;
import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatus;
import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RestCredentialMapper {

    private final RestCredentialAttributeMapper credentialAttributeMapper;
    private final ConfiguredTypesDisplayPropertiesCache configuredTypesDisplayPropertiesCache;
    private final DisplayConfig displayConfig;

    public CredentialDto from(Credential credential, String locale, final VerifiablePresentationStatusResponse validationResults) {
        if (credential == null) {
            return null;
        }
        var credentialSubjectDisplayPropertiesByLocale = configuredTypesDisplayPropertiesCache.getCredentialSubjectDisplayPropertiesByLocale(locale);
        var attributesDisplayProperties = credentialSubjectDisplayPropertiesByLocale.get(credential.getType());

        return CredentialDto.builder()
                .type(credential.getType())
                .status(getStatus(credential, validationResults))
                .validationPolicyResults(getValidationPolicyResults(credential, validationResults))
                .attributes(credentialAttributeMapper.from(
                        displayConfig.getCredentials().get(credential.getType()), credential.getAttributes(), attributesDisplayProperties)
                )
                .issuerDid(credential.getIssuerDid())
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
