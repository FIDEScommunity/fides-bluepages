package community.fides.bluepages.backend.service.oidvcdisplay.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogoProperties {

    private String uri;
    @JsonAlias("alt_text")
    private String altText;

}
