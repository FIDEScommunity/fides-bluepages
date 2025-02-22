package community.fides.bluepages.backend.rest.webpublic.mapper;

import community.fides.bluepages.backend.configuration.DisplayConfig;
import community.fides.bluepages.backend.domain.CredentialAttribute;
import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.domain.DidService;
import community.fides.bluepages.backend.rest.webpublic.dto.DidDto;
import community.fides.bluepages.backend.rest.webpublic.dto.GenericAttribute;
import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatusResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DidMapper {

    private final DidServiceMapper didServiceMapper;
    private final DisplayConfig displayConfig;

    public DidDto from(Did did, String locale) {
        return from(did, locale, Map.of());
    }

    public DidDto globalInfoFrom(Did did, String locale, Map<DidService, VerifiablePresentationStatusResponse> validationResults) {
        return DidDto.builder()
                .id(did.getId())
                .externalKey(did.getExternalKey())
                .did(did.getDid())
                .title(getGenericAttribute(did, "title"))
                .subTitle1(getGenericAttribute(did, "subTitle1"))
                .subTitle2(getGenericAttribute(did, "subTitle2"))
                .logo(getGenericAttribute(did, "logo"))
                .build();
    }

    public DidDto from(Did did, String locale, Map<DidService, VerifiablePresentationStatusResponse> validationResults) {
        return DidDto.builder()
                .id(did.getId())
                .externalKey(did.getExternalKey())
                .did(did.getDid())
                .services(didServiceMapper.from(getSortedServices(did.getServices()), locale, validationResults))
                .title(getGenericAttribute(did, "title"))
                .subTitle1(getGenericAttribute(did, "subTitle1"))
                .subTitle2(getGenericAttribute(did, "subTitle2"))
                .logo(getGenericAttribute(did, "logo"))
                .build();
    }

    private GenericAttribute getGenericAttribute(final Did did, final String attributeName) {
        final DisplayConfig.GenericAttribute attribute = displayConfig.getGenericAttributes().get(attributeName);
        if (attribute == null) {
            return null;
        }
        final GenericAttribute baseAttribute = getAttribute(did.getServices(), attribute.credentialType(), attribute.attribute());
        if ((baseAttribute == null) && (attribute.fallback() != null)) {
            return getAttribute(did.getServices(), attribute.fallback().credentialType(), attribute.fallback().attribute());
        }
        return baseAttribute;
    }

    private GenericAttribute getAttribute(final List<DidService> services, final String credentialType, final String attribute) {
        final Optional<CredentialAttribute> credentialAttribute = services.stream()
                .flatMap(didService -> didService.getCredentials().stream())
                .filter(credential -> credential.getType().equalsIgnoreCase(credentialType))
                .filter(credential -> credential.getAttributes() != null)
                .flatMap(credential -> credential.getAttributes().stream())
                .filter(attr -> attr.getKey().equalsIgnoreCase(attribute))
                .findFirst();
        if (credentialAttribute.isEmpty()) {
            return null;
        }
        return credentialAttribute.map(value -> GenericAttribute.builder()
                .value(value.getActualValue())
                .type(displayConfig.getCredentialAttributeType(credentialType, attribute))
                .build()).orElse(null);
    }

    private List<DidService> getSortedServices(final List<DidService> services) {
        return services.stream()
                .sorted((o1, o2) -> displayConfig.getServiceDisplayOrder(o1.getServiceType()).compareTo(displayConfig.getCredentialDisplayOrder(o2.getServiceType())))
                .toList();
    }

    public List<DidDto> from(List<Did> dids, String locale) {
        return dids.stream().map(did -> from(did, locale)).toList();
    }


}
