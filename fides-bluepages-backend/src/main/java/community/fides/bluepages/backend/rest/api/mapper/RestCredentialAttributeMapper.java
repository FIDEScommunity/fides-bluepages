package community.fides.bluepages.backend.rest.api.mapper;

import community.fides.bluepages.backend.configuration.DisplayConfig;
import community.fides.bluepages.backend.domain.CredentialAttribute;
import community.fides.bluepages.backend.rest.api.dto.CredentialAttributeDto;
import community.fides.bluepages.backend.service.oidvcdisplay.dto.DisplayProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RestCredentialAttributeMapper {

    public CredentialAttributeDto from(final DisplayConfig.CredentialConfig credentialDisplayConfig, CredentialAttribute attribute, final Map<String, DisplayProperties> displayProperties) {
        var displayName = (displayProperties == null || !displayProperties.containsKey(attribute.getKey()))
                ? credentialDisplayConfig.getCredentialAttributeLabel(attribute.getKey())
                : displayProperties.get(attribute.getKey()).getName();

        return CredentialAttributeDto.builder()
                .key(attribute.getKey())
                .value(attribute.getActualValue())
                .displayName(displayName)
                .build();
    }

    public List<CredentialAttributeDto> from(final DisplayConfig.CredentialConfig credentialDisplayConfig, List<CredentialAttribute> attributes, final Map<String, DisplayProperties> displayProperties) {
        return attributes.stream()
                .filter(att -> credentialDisplayConfig != null && credentialDisplayConfig.mustShowAttribute(att))
                .map(att -> from(credentialDisplayConfig, att, displayProperties)).toList();
    }
}
