package community.fides.bluepages.backend.rest.webpublic.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DidForm {

    private String did;
    private boolean performValidations;

}
