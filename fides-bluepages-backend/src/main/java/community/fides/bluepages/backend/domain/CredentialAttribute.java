package community.fides.bluepages.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.ObjectUtils;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "credential_id")
    @ToString.Exclude
    private Credential credential;

    @NotNull
    @Column(name = "attribute_key")
    private String key;

    @Length(max = 1024)
    @Column(name = "attribute_value")
    private String value;

    // Separate value field for large data. Text fields are not searchable with like statements. Therefor large attributes (mainly base64 encode images) will be stored in a separate field.
    @Column(name = "attribute_value_text")
    private String valueText;

    public String getActualValue() {
        if (ObjectUtils.isEmpty(value)) {
            return valueText;
        } else {
            return value;
        }
    }
}
