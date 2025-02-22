package community.fides.bluepages.backend.rest.api.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class DidForm {

    private String did;

}
