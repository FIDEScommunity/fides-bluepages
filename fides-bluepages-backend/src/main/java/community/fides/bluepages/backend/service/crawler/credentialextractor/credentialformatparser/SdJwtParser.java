package community.fides.bluepages.backend.service.crawler.credentialextractor.credentialformatparser;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class SdJwtParser implements CredentialFormatParser {


    @Override
    public boolean canParse(final JsonNode node) {
        if (node == null) {
            return false;
        }
        return (node.has("vct"));
    }

    @Override
    public Optional<CredentialMetaDataDto> parse(final JsonNode node) {
        if (!canParse(node)) {
            return Optional.empty();
        }

        return Optional.of(CredentialMetaDataDto.builder()
                                   .type(getAttribute(node, "vct", "Unknown"))
                                   .issuerDid(getAttribute(node, "iss", ""))
                                   .subjectDid(getAttribute(node, "sub", ""))
                                   .build());
    }

    private String getAttribute(final JsonNode node, final String name, final String defaultValue) {
        if (node.has(name)) {
            return node.get(name).asText();
        }
        return defaultValue;
    }
}
