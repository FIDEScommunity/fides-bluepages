package community.fides.bluepages.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DidWebConfig {

    @Bean("didweb")
    RestTemplate didWebRestTemplate() {
        return new RestTemplate();
    }

}
