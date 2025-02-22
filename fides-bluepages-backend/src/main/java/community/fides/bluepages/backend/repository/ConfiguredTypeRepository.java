package community.fides.bluepages.backend.repository;

import community.fides.bluepages.backend.domain.ConfiguredType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguredTypeRepository extends JpaRepository<ConfiguredType, Long> {

    Optional<ConfiguredType> findByCredentialType(String credentialType);
}


