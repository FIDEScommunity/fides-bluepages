package community.fides.bluepages.backend.service.crawler.credentialextractor.credentialformatparser;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class JsonLdParser implements CredentialFormatParser {


    @Override
    public boolean canParse(final JsonNode node) {
        if (node == null || !node.has("@context")) {
            return false;
        }
        final var contextNode = node.get("@context");
        if (contextNode != null && contextNode.isArray()) {
            return contextNode.get(0).asText().equals("https://www.w3.org/ns/credentials/v2");
        }
        return false;
    }

    @Override
    public Optional<CredentialMetaDataDto> parse(final JsonNode node) {
        if (!canParse(node)) {
            return Optional.empty();
        }

        return Optional.of(CredentialMetaDataDto.builder()
                                   .type(getType(node))
                                   .issuerDid(getAttribute(node, "iss", ""))
                                   .subjectDid(getAttribute(node, "sub", ""))
                                   .build());
    }

    private String getType(final JsonNode node) {
        if (node.has("type")) {
            JsonNode typeNode = node.get("type");
            if (typeNode != null && typeNode.isArray()) {
                return typeNode.get(typeNode.size() - 1).asText();
            }
        }
        return "Unknown";
    }

    private @NotNull String getAttribute(final JsonNode node, final String name, final String defaultValue) {
        if (node.has(name)) {
            JsonNode attr = node.get(name);
            if (attr != null) {
                return attr.asText();
            }
        }
        return defaultValue;
    }
}
