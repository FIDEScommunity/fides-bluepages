package community.fides.bluepages.backend.service.crawler.credentialextractor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import community.fides.bluepages.backend.domain.Credential;
import community.fides.bluepages.backend.domain.CredentialAttribute;
import community.fides.bluepages.backend.domain.CredentialStatus;
import community.fides.bluepages.backend.service.crawler.credentialextractor.credentialformatparser.CredentialFormatParser;
import community.fides.bluepages.backend.service.crawler.credentialextractor.credentialformatparser.CredentialMetaDataDto;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class VerifiableCredentialBuilder {

    private final ObjectMapper objectMapper;
    private final List<CredentialFormatParser> credentialFormatParsers;

    @SneakyThrows
    public Optional<Credential> extractCredential(final String vcJson, final List<String> disclosures) {
        final JsonNode jsonNode = objectMapper.readTree(vcJson);
        final Optional<CredentialMetaDataDto> credentialMetaData = getCredentialMetaData(jsonNode);
        if (credentialMetaData.isEmpty()) {
            return Optional.empty();
        }
        Credential credential = new Credential();
        credential.setType(credentialMetaData.get().getType());
        credential.setIssuerDid(credentialMetaData.get().getIssuerDid());
        credential.setSubjectDid(credentialMetaData.get().getSubjectDid());
        credential.setLastUpdated(LocalDateTime.now());
        credential.setStatus(CredentialStatus.UNCHECKED);
        credential.setAttributes(getAttributes(jsonNode, credential, disclosures));
        return Optional.of(credential);
    }

    private @NotNull List<CredentialAttribute> getAttributes(JsonNode jsonNode, Credential credential, List<String> disclosures) {
        var credentialNode = jsonNode;
        if (credentialNode.has("vc")) {
            // VCDM 1.0
            credentialNode = credentialNode.get("vc").get("credentialSubject");
        } else if (credentialNode.has("credentialSubject")) {
            // VCDM 2.0
            credentialNode = credentialNode.get("credentialSubject");
        }
        final var attributes = StreamSupport.stream(Spliterators.spliteratorUnknownSize(credentialNode.fields(), Spliterator.ORDERED), false)
                .flatMap(entry -> buildAttributeList(entry, credential, "").stream())
                .toList();
        final var disclosedAttributes = disclosures.stream().map(disclosure -> createAttributeFromDisclosure(credential, disclosure)).toList();
        final var result = new ArrayList<>(attributes);
        result.addAll(disclosedAttributes);
        return result;
    }

    private CredentialAttribute createAttributeFromDisclosure(final Credential credential, final String disclosue) {
        final var decodedDisclosure = new String(Base64.getDecoder().decode(disclosue));
        String[] parts = decodedDisclosure
                            .replace("[", "")
                            .replace("]", "")
                            .replace("\"", "")
                            .split(",");

        String attribute = parts[1].trim();
        String value = parts[2].trim();
        return CredentialAttribute.builder()
                .key(parts[1].trim())
                .value(parts[2].trim().length() <= 1024 ? parts[2].trim() : "")
                .valueText(parts[2].trim().length() > 1024 ? parts[2].trim() : "")
                .credential(credential)
                .build();
    }

    private Optional<CredentialMetaDataDto> getCredentialMetaData(final JsonNode jsonNode) {
        return credentialFormatParsers.stream()
                .filter(credentialFormatParser -> credentialFormatParser.canParse(jsonNode))
                .map(credentialFormatParser -> credentialFormatParser.parse(jsonNode))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    private List<CredentialAttribute> buildAttributeList(final Map.Entry<String, JsonNode> entry, final Credential credential, String prefix) {
        if (entry.getValue().isObject()) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(entry.getValue().fields(), Spliterator.ORDERED), false)
                    .flatMap(childEntry -> buildAttributeList(childEntry, credential, addPrefix(prefix, entry.getKey())).stream())
                    .toList();
        }
        if (entry.getValue().isArray()) {
            return List.of(buildAttribute(entry.getKey(), entry.getValue(), credential, prefix));
        }
        return List.of(buildAttribute(entry.getKey(), entry.getValue(), credential, prefix));
    }

    private CredentialAttribute buildAttribute(final String key, final JsonNode valueNode, final Credential credential, final String prefix) {
        final String attributeKey = addPrefix(prefix, key);
        final String rawValue = valueNode.isValueNode() ? valueNode.asText() : valueNode.toString();
        return CredentialAttribute.builder()
                .key(attributeKey)
                .value(rawValue.length() <= 1024 ? rawValue : "")
                .valueText(rawValue.length() > 1024 ? rawValue : "")
                .credential(credential)
                .build();
    }

    private @NotNull String addPrefix(final String prefix, final String key) {
        if (prefix != null && !prefix.isEmpty()) {
            return prefix + "." + key;
        }
        return key;
    }

}
