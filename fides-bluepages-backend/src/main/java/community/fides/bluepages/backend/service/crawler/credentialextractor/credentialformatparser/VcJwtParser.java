package community.fides.bluepages.backend.service.crawler.credentialextractor.credentialformatparser;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class VcJwtParser implements CredentialFormatParser {


    @Override
    public boolean canParse(final JsonNode node) {
        if (node == null) {
            return false;
        }
        return (node.has("vc"));
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
        if (node.get("vc").has("type")) {
            JsonNode typeNode = node.get("vc").get("type");
            if (typeNode != null && typeNode.isArray()) {
                return typeNode.get(typeNode.size() - 1).asText();
            }
        }
        return "Unknown";
    }

    private @NotNull String getIssuerDid(final JsonNode node) {
        if (node.get("vc").has("issuer")) {
            JsonNode issuer = node.get("vc").get("issuer");
            if (issuer != null) {
                return issuer.asText();
            }
        }
        return "";
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
