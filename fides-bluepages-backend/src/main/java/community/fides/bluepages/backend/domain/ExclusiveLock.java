package community.fides.bluepages.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exclusive_lock")
public class ExclusiveLock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The lock type is used as unique value in the database to prevent duplicate records.
    @NotNull
    @Column(name = "lock_type")
    private String lockType;

    @NotNull
    @Column(name = "locked_owner")
    private String lockedOwner;

    @NotNull
    @Column(name = "lock_expiration_date_time")
    private LocalDateTime lockExpirationDateTime;

    public boolean isLockExpired() {
        return lockExpirationDateTime.isBefore(LocalDateTime.now());
    }

}
