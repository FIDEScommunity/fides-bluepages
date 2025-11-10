package community.fides.bluepages.backend.rest.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CredentialAttributeDto {
    private String key;
    private String value;
    private String displayName;
}
