package community.fides.bluepages.backend.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
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
public class DidService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String externalKey = UUID.randomUUID().toString();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "did_id")
    @ToString.Exclude
    private Did did;

    private String serviceId;
    // If the type is a primitive string this field is used.
    private String serviceType;
    // If the type is not a primitive string this field is used and filled with the json value.
    private String serviceTypeJson;
    // If the endpoint is a primitive string this field is used.
    // Example with input: "serviceEndpoint": "https://bar.example.com"
    // serviceEndpoint is filled with https://bar.example.com
    private String serviceEndpoint;
    // If the endpoint is not a primitive string this field is used and filled with the json value.
    // Example with input: "serviceEndpoint": {
    //        "origins": ["https://foo.example.com", "https://identity.foundation"]
    //      }
    // serviceEndpointJson is filled with: {"origins": ["https://foo.example.com", "https://identity.foundation"]}
    private String serviceEndpointJson;

    @Builder.Default
    @OneToMany(mappedBy = "didService", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Credential> credentials = new LinkedList<>();

    public boolean isLinkedVerifiablePresentation() {
        if ("LinkedVerifiablePresentation".equalsIgnoreCase(serviceType)) {
            return true;
        }
        if ((serviceTypeJson != null) && (serviceTypeJson.contains("LinkedVerifiablePresentation"))) {
            return true;
        }
        return false;
    }
}
