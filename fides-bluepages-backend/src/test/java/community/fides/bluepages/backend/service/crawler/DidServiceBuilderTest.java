package community.fides.bluepages.backend.service.crawler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import community.fides.bluepages.backend.domain.Credential;
import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.domain.DidService;
import community.fides.bluepages.backend.service.crawler.credentialextractor.CredentialExtractor;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DidServiceBuilderTest {

    @InjectMocks
    private DidServiceBuilder sut;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @Spy
    private List<CredentialExtractor> credentialExtractors = new LinkedList<>();

    @Mock
    private CredentialExtractor credentialExtractor;

    @Test
    void build() {
        // Arrange
        final Did did = new Did();
        final JsonNode jsonNode = parseJsonFromClasspath("did/did-service-builder-test.json");
        credentialExtractors.clear();
        credentialExtractors.add(credentialExtractor);
        when(credentialExtractor.extractCredentials(any())).thenAnswer(invocationOnMock -> List.of(Credential.builder().build()));

        // Act
        final List<DidService> didServices = sut.buildServices(did, jsonNode);

        // Assert
        assertThat(didServices).hasSize(2)
                .extracting(DidService::getDid)
                .containsExactly(did, did);

        assertThat(didServices.get(0).getServiceId()).isEqualTo("did:example:123#foo");
        assertThat(didServices.get(0).getServiceType()).isEqualTo("LinkedDomains");
        assertThat(didServices.get(0).getServiceTypeJson()).isNull();
        assertThat(didServices.get(0).getServiceEndpoint()).isNull();
        assertThat(didServices.get(0).getServiceEndpointJson()).isEqualTo("{\"origins\":[\"https://foo.example.com\",\"https://identity.foundation\"]}");
        assertThat(didServices.get(0).getCredentials()).hasSize(1)
                .extracting(Credential::getDidService)
                .contains(didServices.get(0));

        assertThat(didServices.get(1).getServiceId()).isEqualTo("did:example:123#bar");
        assertThat(didServices.get(1).getServiceType()).isNull();
        assertThat(didServices.get(1).getServiceTypeJson()).isEqualTo("[\"LinkedDomains\",\"ExtraDomains\"]");
        assertThat(didServices.get(1).getServiceEndpoint()).isEqualTo("https://bar.example.com");
        assertThat(didServices.get(1).getServiceEndpointJson()).isNull();
        assertThat(didServices.get(1).getCredentials()).hasSize(1)
                .extracting(Credential::getDidService)
                .contains(didServices.get(1));

    }

    private JsonNode parseJsonFromClasspath(String path) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + path);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON from classpath: " + path, e);
        }
    }
}
