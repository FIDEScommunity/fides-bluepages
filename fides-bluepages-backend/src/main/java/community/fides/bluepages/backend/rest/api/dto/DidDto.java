package community.fides.bluepages.backend.rest.api.dto;

import community.fides.bluepages.backend.domain.Did;
import java.util.List;

import community.fides.bluepages.backend.rest.webpublic.dto.GenericAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class DidDto {

    private String did;
    private GenericAttribute title;
    private GenericAttribute subTitle1;
    private GenericAttribute subTitle2;
    private GenericAttribute logo;


    public static DidDto from(Did did) {
        return DidDto.builder()
                .did(did.getDid())
                .build();
    }

    public static List<DidDto> from(List<Did> dids) {
        if (dids == null) {
            return List.of();
        }
        return dids.stream()
                .map(DidDto::from)
                .toList();
    }
}
