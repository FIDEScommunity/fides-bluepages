package community.fides.bluepages.backend.service.crawler.credentialextractor.credentialformatparser;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Optional;

public interface CredentialFormatParser {

    boolean canParse(JsonNode node);

    Optional<CredentialMetaDataDto> parse(JsonNode node);
}
