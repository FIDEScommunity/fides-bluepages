package community.fides.bluepages.backend.service.crawler.credentialextractor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;
import community.fides.bluepages.backend.domain.Credential;
import community.fides.bluepages.backend.domain.DidService;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
public class LinkedVerifiablePresentationCredentialExtractor implements CredentialExtractor {

    private final ObjectMapper objectMapper;
    private final VerifiableCredentialBuilder verifiableCredentialBuilder;

    @SneakyThrows
    @Override
    public List<Credential> extractCredentials(final DidService didService) {
        if (!"LinkedVerifiablePresentation".equalsIgnoreCase(didService.getServiceType()) || didService.getServiceEndpoint() == null) {
            return List.of();
        }

        try {
            final ResponseEntity<String> jwtResponse = new RestTemplate().getForEntity(didService.getServiceEndpoint(), String.class);
            if (!jwtResponse.getStatusCode().is2xxSuccessful() || jwtResponse.getBody() == null) {
                return List.of();
            }
            final SignedJWT jwt = SignedJWT.parse(jwtResponse.getBody());
            final JsonNode jsonNode = objectMapper.readTree(jwt.getPayload().toString());

            if (jsonNode.has("vp") && jsonNode.get("vp").has("verifiableCredential") && jsonNode.get("vp").get("verifiableCredential").isArray()) {
                return StreamSupport.stream(jsonNode.get("vp").get("verifiableCredential").spliterator(), false)
                        .map(JsonNode::asText)
                        .map(this::extractCredential)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList();
            }
            return List.of();
        } catch (RestClientException e) {
            return List.of();
        }

    }

    @SneakyThrows
    private Optional<Credential> extractCredential(final String vcJwt) {
        final SignedJWT jwt = SignedJWT.parse(vcJwt);
        return verifiableCredentialBuilder.extractCredential(jwt.getPayload().toString());
    }

}
