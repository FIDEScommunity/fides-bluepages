package community.fides.bluepages.backend.rest.api.mapper;

import community.fides.bluepages.backend.configuration.DisplayConfig;
import community.fides.bluepages.backend.domain.CredentialAttribute;
import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.domain.DidService;
import community.fides.bluepages.backend.rest.api.dto.DidDto;
import community.fides.bluepages.backend.rest.webpublic.dto.GenericAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestDidMapper {
    private final DisplayConfig displayConfig;

    public DidDto from(Did did) {
        return DidDto.builder()
                .did(did.getDid())
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

    public List<DidDto> from(List<Did> dids) {
        return dids.stream().map(this::from).toList();
    }
}
