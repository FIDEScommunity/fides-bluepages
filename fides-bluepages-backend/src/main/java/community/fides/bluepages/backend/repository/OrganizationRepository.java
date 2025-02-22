package community.fides.bluepages.backend.repository;

import community.fides.bluepages.backend.domain.Organization;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByExternalKey(String externalKey);

    Optional<Organization> findByApiKey(String apiKey);
}


