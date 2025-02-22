package community.fides.bluepages.backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import static java.util.Collections.singletonList;

@Configuration
public class OrganizationalWalletConfig {

    @Bean("organizationalWalletClient")
    RestTemplate organizationalWalletRestTemplate(@Value("${externalServices.organizational-wallet.apiKey}") String apiKey,
                                      @Value("${externalServices.organizational-wallet.apiBaseUrl}") String baseUrl,
                                      RestTemplateBuilder restTemplateBuilder
    ) {
        return restTemplateBuilder
                .rootUri(baseUrl)
                .requestFactory(SimpleClientHttpRequestFactory::new)
                .additionalInterceptors(singletonList((request, body, execution) -> {
                    HttpHeaders headers = request.getHeaders();
                    headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
                    headers.add("x-api-key", apiKey);
                    return execution.execute(request, body);
                }))
                .build();
    }

}
