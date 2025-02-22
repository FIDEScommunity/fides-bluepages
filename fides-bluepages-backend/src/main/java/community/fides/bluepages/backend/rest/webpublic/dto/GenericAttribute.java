package community.fides.bluepages.backend.rest.webpublic.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenericAttribute {

    private String value;
    private String type;

}
