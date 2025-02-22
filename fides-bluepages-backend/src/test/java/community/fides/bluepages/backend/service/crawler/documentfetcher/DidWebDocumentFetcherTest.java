package community.fides.bluepages.backend.service.crawler.documentfetcher;

import community.fides.bluepages.backend.domain.Did;
import java.net.URI;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DidWebDocumentFetcherTest {

    @InjectMocks
    private DidWebDocumentFetcher sut;

    @Mock
    private RestTemplate restTemplate;

    @Captor
    ArgumentCaptor<URI> uriArgumentCaptor;

    @ParameterizedTest
    @MethodSource("uris")
    void givenADidWeb_whenFetchRawData_thenCheckResult(String did, URI expectedUri) {
        // Arrange
        final Did theDid = Did.builder()
                .did(did)
                .build();
        when(restTemplate.getForObject(uriArgumentCaptor.capture(), eq(byte[].class))).thenReturn("resultBody".getBytes());

        // Act
        final byte[] bytes = sut.fetchRawData(theDid);

        // Assert
        assertThat(uriArgumentCaptor.getValue()).isEqualTo(expectedUri);
        assertThat(new String(bytes)).isEqualTo("resultBody");

    }

    private static Stream<Arguments> uris() {
        return Stream.of(
                Arguments.of("did:web:host.credenco.com", URI.create("https://host.credenco.com/.well-known/did.json")),
                Arguments.of("did:web:host.credenco.com:did", URI.create("https://host.credenco.com/did/did.json")),
                Arguments.of("did:web:host.credenco.com:did:abc", URI.create("https://host.credenco.com/did/abc/did.json")),
                Arguments.of("did:web:host.credenco.com:8080:did:abc", URI.create("https://host.credenco.com:8080/did/abc/did.json")),
                Arguments.of("did:web:host.credenco.com%3A8080:did:abc", URI.create("https://host.credenco.com:8080/did/abc/did.json"))
        );
    }

    @Test
    void givenADidWeb_whenCanFetchRawData_thenCheckResult() {
        assertThat(sut.canFetchRawData(did("did:web:"))).isTrue();
        assertThat(sut.canFetchRawData(did("did:web:q"))).isTrue();
        assertThat(sut.canFetchRawData(did("did:ebsi:q"))).isFalse();
    }

    private Did did(final String did) {
        return Did.builder()
                .did(did)
                .build();
    }
}
