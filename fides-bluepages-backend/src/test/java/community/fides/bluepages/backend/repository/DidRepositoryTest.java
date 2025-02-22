package community.fides.bluepages.backend.repository;

import community.fides.bluepages.backend.domain.Did;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class DidRepositoryTest extends JpaIntegrationTest {


    @Autowired
    private DidRepository sut;

    @BeforeEach
    void setUp() {
        sut.deleteAll();
    }

    @AfterEach
    void tearDown() {
        sut.deleteAll();
    }

    @Test
    void givenSomeDids_whenFindDidsToCrawl_thenExpectCorrectPartition() {
        // Arrange
        storeDid(LocalDateTime.now().minusYears(1), LocalDateTime.now());
        storeDid(LocalDateTime.now().minusYears(1), LocalDateTime.now());
        storeDid(LocalDateTime.now().minusYears(1), LocalDateTime.now());

        // Act
        final int totalPartitions = 2;
        final int partition = 1;
        final List<Long> didsToCrawl = sut.findDidsToCrawl(partition, totalPartitions, LocalDateTime.now().minusHours(1), LocalDateTime.now(), LocalDateTime.now());

        // Assert
        final long expectedCount = sut.findAll().stream()
                .filter(did -> did.getId() % totalPartitions == partition)
                .count();
        assertThat(didsToCrawl).hasSize((int) expectedCount);
    }

    @Test
    void givenSomeDids_whenFindDidsToCrawl_thenExpectCorrectDidsBasedOnLastCrawledAtAndLastUpdatedAt() {
        // Arrange
        Did notRecentlyChangedAndNotRecentlyCrawledDid = storeDid(createDid()
                                                                          .rawDataLastUpdatedAt(LocalDateTime.now().minusYears(1))
                                                                          .lastCrawledAt(LocalDateTime.now().minusYears(1))
        );
        Did notRecentlyChangedAndRecentlyCrawledDid = storeDid(createDid()
                                                                       .rawDataLastUpdatedAt(LocalDateTime.now().minusYears(1))
                                                                       .lastCrawledAt(LocalDateTime.now().minusMinutes(1))
        );
        Did recentlyChangedAndNotRecentlyCrawledDid = storeDid(createDid()
                                                                       .rawDataLastUpdatedAt(LocalDateTime.now().minusMinutes(5))
                                                                       .lastCrawledAt(LocalDateTime.now().minusYears(5))
        );
        Did recentlyChangedAndRecentlyCrawledDid = storeDid(createDid()
                                                                    .rawDataLastUpdatedAt(LocalDateTime.now().minusMinutes(1))
                                                                    .lastCrawledAt(LocalDateTime.now().minusMinutes(5))
        );

        // Act
        final LocalDateTime recentlyChangedPointInTime = LocalDateTime.now().minusDays(1);
        LocalDateTime recentlyChangedCrawlTimestamp = LocalDateTime.now().minusHours(1);
        LocalDateTime nonRecentlyChangedCrawlTimestamp = LocalDateTime.now().minusDays(1);
        final List<Long> didsToCrawl = sut.findDidsToCrawl(0, 1, recentlyChangedPointInTime, recentlyChangedCrawlTimestamp, nonRecentlyChangedCrawlTimestamp);

        // Assert
        log.info("notRecentlyChangedAndNotRecentlyCrawledDid.getId(): {}", notRecentlyChangedAndNotRecentlyCrawledDid.getId());
        log.info("notRecentlyChangedAndRecentlyCrawledDid: {}", notRecentlyChangedAndRecentlyCrawledDid.getId());
        log.info("recentlyChangedAndNotRecentlyCrawledDid: {}", recentlyChangedAndNotRecentlyCrawledDid.getId());
        log.info("recentlyChangedAndRecentlyCrawledDid: {}", recentlyChangedAndRecentlyCrawledDid.getId());
        assertThat(didsToCrawl).contains(notRecentlyChangedAndNotRecentlyCrawledDid.getId());
        assertThat(didsToCrawl).doesNotContain(notRecentlyChangedAndRecentlyCrawledDid.getId());
        assertThat(didsToCrawl).contains(recentlyChangedAndNotRecentlyCrawledDid.getId());
        assertThat(didsToCrawl).doesNotContain(recentlyChangedAndRecentlyCrawledDid.getId());
    }


    private Did storeDid(final LocalDateTime lastCrawledAt, final LocalDateTime lastUpdatedAt) {
        return sut.save(
                Did.builder()
                        .did("did:example:" + UUID.randomUUID())
                        .externalKey(UUID.randomUUID().toString())
                        .lastCrawledAt(lastCrawledAt)
                        .rawDataLastUpdatedAt(lastUpdatedAt)
                        .registeredAt(LocalDateTime.now())
                        .build()
        );
    }

    private Did storeDid(Did.DidBuilder didBuilder) {
        return sut.save(didBuilder.build());
    }

    private Did.DidBuilder createDid() {
        return Did.builder()
                .did("did:example:" + UUID.randomUUID())
                .externalKey(UUID.randomUUID().toString())
                .registeredAt(LocalDateTime.now());
    }
}
