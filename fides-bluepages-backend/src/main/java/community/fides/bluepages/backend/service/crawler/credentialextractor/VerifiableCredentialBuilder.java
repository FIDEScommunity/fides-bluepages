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
    public Optional<Credential> extractCredential(final String vcJson) {
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
        credential.setAttributes(StreamSupport.stream(Spliterators.spliteratorUnknownSize(jsonNode.get("vc").get("credentialSubject").fields(), Spliterator.ORDERED), false)
                                         .flatMap(entry -> buildAttributeList(entry, credential, "").stream())
                                         .toList());
        return Optional.of(credential);
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
        return List.of(CredentialAttribute.builder()
                               .key(addPrefix(prefix, entry.getKey()))
                               .value(entry.getValue().asText().length() <= 1024 ? entry.getValue().asText() : "")
                               .valueText(entry.getValue().asText().length() > 1024 ? entry.getValue().asText() : "")
                               .credential(credential)
                               .build());
    }

    private @NotNull String addPrefix(final String prefix, final String key) {
        if (prefix != null && !prefix.isEmpty()) {
            return prefix + "." + key;
        }
        return key;
    }

}
