package community.fides.bluepages.backend.configuration;

import java.time.Duration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "crawler")
@Getter
@Setter
public class CrawlerConfig {

    private boolean enabled;
    private boolean ebsiApiCrawlerEnabled;

    private int maxNumberOfConcurrentCrawls = 10;
    /**
     * Documents are marked as recently changed when the last update is not older than this value. So when set to PT24H all documents changed in the last 24 hours are marked as recentlyUpdated
     */
    private Duration recentlyChangedDuration = Duration.ofDays(1);

    private Duration crawlFrequencyRecentlyUpdatedDids = Duration.ofHours(1);

    private Duration crawlFrequencyNotRecentlyUpdatedDids = Duration.ofDays(1);
}
