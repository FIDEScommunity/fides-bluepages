package community.fides.bluepages.backend.service.crawler;

import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.service.ConfiguredTypeService;
import community.fides.bluepages.backend.service.crawler.documentfetcher.DidDocumentFetcher;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrawlerTest {

    @InjectMocks
    private Crawler sut;

    @Spy
    private List<DidDocumentFetcher> didDocumentFetchers = new LinkedList<>();

    @Mock
    private DidDocumentFetcher didDocumentFetcher;

    @Mock
    private DidDocumentBuilder didDocumentBuilder;

    @Mock
    private ConfiguredTypeService configuredTypeService;

    @BeforeEach
    void setUp() {
        didDocumentFetchers.clear();
        didDocumentFetchers.add(didDocumentFetcher);
    }

    @Test
    void crawlDidDocument() {
        // Arrange
        final String testDid = "did:qqq:abc";
        when(didDocumentFetcher.canFetchRawData(isA(Did.class))).thenReturn(true);
        final byte[] didContent = "testContent".getBytes(StandardCharsets.UTF_8);
        when(didDocumentFetcher.fetchRawData(isA(Did.class))).thenReturn(didContent);
        final Did newDid = Did.builder().did("did:qqq:abc").build();
        when(didDocumentBuilder.build(isA(Did.class), eq(didContent))).thenReturn(newDid);
        final Did updatedDid = newDid.toBuilder().build();
        when(configuredTypeService.removeNotAllowedTypes(newDid)).thenReturn(updatedDid);
        when(configuredTypeService.containsAtLeastOneRequiredServiceOrCredential(newDid)).thenReturn(true);

        // Act
        final Optional<Did> crawledDidDocument = sut.crawlDidDocument(testDid);

        // Assert
        assertThat(crawledDidDocument.isPresent()).isTrue();
        assertThat(crawledDidDocument.get().getDid()).isEqualTo(testDid);
        assertThat(crawledDidDocument.get().meetsTypeRequirements()).isEqualTo(true);

    }

}
