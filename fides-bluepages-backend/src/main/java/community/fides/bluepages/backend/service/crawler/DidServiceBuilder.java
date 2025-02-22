package community.fides.bluepages.backend.service.crawler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import community.fides.bluepages.backend.domain.Credential;
import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.domain.DidService;
import community.fides.bluepages.backend.service.crawler.credentialextractor.CredentialExtractor;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DidServiceBuilder {

    private final ObjectMapper objectMapper;
    private final List<CredentialExtractor> credentialExtractors;

    public List<DidService> buildServices(final Did did, final JsonNode jsonNode) {
        if (jsonNode.get("service") == null || !jsonNode.get("service").isArray()) {
            return Collections.emptyList();
        }

        return StreamSupport.stream(jsonNode.get("service").spliterator(), false)
                .flatMap(serviceNode -> buildDidServices(did, serviceNode).stream())
                .toList();
    }

    @SneakyThrows
    private List<DidService> buildDidServices(final Did did, final JsonNode serviceNode) {
        if (isLinkedPresentation(serviceNode)) {
            return getLinkedVerifiablePresentationDidServices(did, serviceNode);
        } else {
            return List.of(createDidService(did, serviceNode));
        }

    }

    private List<DidService> getLinkedVerifiablePresentationDidServices(final Did did, final JsonNode serviceNode) {
        List<String> urls = getServiceEndpointUrls(serviceNode);
        if (urls.isEmpty()) {
            return List.of(createDidService(did, serviceNode));
        } else {
            return urls.stream().
                    map(serviceEndpointUrl -> {
                        final DidService didService = getDidServiceBuilder(did, serviceNode)
                                .serviceEndpoint(serviceEndpointUrl)
                                .serviceEndpointJson(null)
                                .build();
                        addCredentials(didService);
                        return didService;
                    }).toList();
        }

    }

    private boolean isLinkedPresentation(final JsonNode serviceNode) {
        return serviceNode.get("type").isTextual() && "LinkedVerifiablePresentation".equalsIgnoreCase(serviceNode.get("type").asText());
    }

    @SneakyThrows
    private DidService createDidService(Did did, JsonNode serviceNode) {
        final DidService didService = getDidServiceBuilder(did, serviceNode).build();
        addCredentials(didService);
        return didService;
    }

    @SneakyThrows
    private DidService.DidServiceBuilder getDidServiceBuilder(final Did did, final JsonNode serviceNode) {
        return DidService.builder()
                .did(did)
                .serviceId(serviceNode.get("id").asText())
                .serviceType(serviceNode.get("type").isTextual() ? serviceNode.get("type").asText() : null)
                .serviceTypeJson(serviceNode.get("type").isTextual() ? null : objectMapper.writeValueAsString(serviceNode.get("type")))
                .serviceEndpoint(serviceNode.get("serviceEndpoint").isTextual() ? serviceNode.get("serviceEndpoint").asText() : null)
                .serviceEndpointJson(serviceNode.get("serviceEndpoint").isTextual() ? null : objectMapper.writeValueAsString(serviceNode.get("serviceEndpoint")));
    }

    private List<String> getServiceEndpointUrls(final JsonNode serviceNode) {
        List<String> urls = new LinkedList<>();
        if (serviceNode.get("serviceEndpoint").isTextual()) {
            urls.add(serviceNode.get("serviceEndpoint").asText());
        }
        if (!serviceNode.get("serviceEndpoint").isTextual()) {
            urls.addAll(StreamSupport.stream(serviceNode.get("serviceEndpoint").spliterator(), false)
                                .map(JsonNode::asText)
                                .filter(element -> element.startsWith("http"))
                                .toList());
        }
        return urls;
    }

    private void addCredentials(final DidService didService) {
        final List<Credential> extractedCredentials = credentialExtractors.stream()
                .map(credentialExtractor -> credentialExtractor.extractCredentials(didService))
                .filter(credentials -> (credentials != null) && !credentials.isEmpty())
                .flatMap(credentials -> credentials.stream())
                .peek(credential -> credential.setDidService(didService))
                .toList();
        didService.setCredentials(extractedCredentials);
    }


}
