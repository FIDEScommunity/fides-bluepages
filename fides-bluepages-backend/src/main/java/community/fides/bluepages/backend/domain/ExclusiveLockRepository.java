package community.fides.bluepages.backend.domain;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExclusiveLockRepository extends JpaRepository<ExclusiveLock, Long> {

    Optional<ExclusiveLock> findByLockType(String kafkaLockType);

    Optional<ExclusiveLock> findByLockTypeAndLockExpirationDateTimeGreaterThan(String kafkaLockType, LocalDateTime lockExpirationDateTime);

    Optional<ExclusiveLock> findByLockTypeAndLockedOwner(String kafkaLockType, String lockedOwner);

    @Transactional
    @Modifying
    @Query(value = """
            INSERT INTO exclusive_lock (lock_type, locked_owner, lock_expiration_date_time) 
            VALUES (:lockType, :lockedOwner, :lockExpirationDateTime); 
            """,
            nativeQuery = true)
    int insertNewLock(@Param("lockType") String lockType, @Param("lockedOwner") String lockedOwner, @Param("lockExpirationDateTime") LocalDateTime lockExpirationDateTime);

    @Transactional
    void delete(ExclusiveLock kafkaLock);
}
