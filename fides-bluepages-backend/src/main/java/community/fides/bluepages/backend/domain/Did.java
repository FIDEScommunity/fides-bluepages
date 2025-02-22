package community.fides.bluepages.backend.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Did {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String externalKey;

    @NotNull
    private String did;

    private byte[] rawData;

    private byte[] rawDataHash;
    private LocalDateTime rawDataLastUpdatedAt;
    private LocalDateTime lastCrawledAt;
    private LocalDateTime registeredAt;

    @Setter
    private boolean meetsTypeRequirements;

    @Builder.Default
    @OneToMany(mappedBy = "did", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<DidService> services = new LinkedList<>();

    public boolean meetsTypeRequirements() {
        return meetsTypeRequirements;
    }
}
