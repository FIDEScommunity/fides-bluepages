package community.fides.bluepages.backend.rest.webpublic.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DidDto {

    private Long id;
    private String externalKey;
    private String did;
    private List<DidServiceDto> services;
    private GenericAttribute title;
    private GenericAttribute subTitle1;
    private GenericAttribute subTitle2;
    private GenericAttribute logo;

}
