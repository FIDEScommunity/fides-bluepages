package community.fides.bluepages.backend.service.crawler;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import community.fides.bluepages.backend.domain.Did;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DidDocumentBuilder {

    private final ObjectMapper objectMapper;
    private final DidServiceBuilder didServiceBuilder;

    @SneakyThrows
    public Did build(Did did, byte[] rawData) {
        final JsonNode jsonNode = objectMapper.readTree(rawData);

        did.setRawData(rawData);
        did.setServices(didServiceBuilder.buildServices(did, jsonNode));
        return did;
    }

}
