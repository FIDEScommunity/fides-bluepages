package community.fides.bluepages.backend.rest.webpublic.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CredentialAttributeDto {

    private Long id;
    private String key;
    private String value;
    private String displayName;
    private String dataType;
}
