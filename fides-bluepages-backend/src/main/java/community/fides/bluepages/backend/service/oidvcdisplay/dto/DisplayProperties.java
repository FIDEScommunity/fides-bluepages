package community.fides.bluepages.backend.service.oidvcdisplay.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DisplayProperties {

    private String name;
    private String locale;
    private LogoProperties logo;
    private String description;
    @JsonAlias("background_image")
    private BackgroundImage backgroundImage;
    @JsonAlias("background_color")
    private String backgroundColor;
    @JsonAlias("text_color")
    private String textColor;

}
