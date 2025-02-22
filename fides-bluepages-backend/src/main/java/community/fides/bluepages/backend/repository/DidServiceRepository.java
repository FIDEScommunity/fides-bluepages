package community.fides.bluepages.backend.repository;

import community.fides.bluepages.backend.domain.DidService;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DidServiceRepository extends JpaRepository<DidService, Long> {

    Optional<DidService> findByDid_ExternalKeyAndExternalKey(@NotNull String didExternalKey, @NotNull String externalKey);

}


