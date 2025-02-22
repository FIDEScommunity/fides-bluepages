package community.fides.bluepages.backend.repository;

import community.fides.bluepages.backend.domain.Credential;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Optional<Credential> findFirstBySubjectDid(String subjectDid);

}


