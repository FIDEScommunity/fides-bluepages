package community.fides.bluepages.backend.service;

import community.fides.bluepages.backend.domain.ExclusiveLock;
import community.fides.bluepages.backend.domain.ExclusiveLockRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Slf4j
@Validated
@Component
public class ExclusiveLockService {

    public static final int DEFAULT_LOCK_DURATION_IN_MINUTES = 15;

    private final ExclusiveLockRepository exclusiveLockRepository;
    private String hostname = null;

    @SneakyThrows
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Optional<ExclusiveLock> obtainLock(@Valid @Size(max = 30) String lockType) {
        return obtainLock(lockType, DEFAULT_LOCK_DURATION_IN_MINUTES);
    }

    @SneakyThrows
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Optional<ExclusiveLock> obtainLock(@Valid @Size(max = 30) String lockType, int lockDurationInMinutes) {
        return obtainLock(getLockOwner(), lockType, lockDurationInMinutes);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Optional<ExclusiveLock> obtainLock(@Valid @Size(max = 100) String lockOwner, @Valid @Size(max = 30) String lockType, int lockDurationInMinutes) {
        return obtainLock(lockOwner, lockType, LocalDateTime.now().plusMinutes(lockDurationInMinutes));
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Optional<ExclusiveLock> obtainLock(@Valid @Size(max = 100) String lockOwner, @Valid @Size(max = 30) String lockType,
                                              LocalDateTime lockExpirationDateTime) {
        Optional<ExclusiveLock> currentLockOptional = exclusiveLockRepository.findByLockType(lockType);
        if (currentLockOptional.isPresent()) {
            ExclusiveLock exclusiveLock = currentLockOptional.get();
            if (exclusiveLock.getLockedOwner().equals(lockOwner)) {
                return Optional.of(renewLock(exclusiveLock, lockExpirationDateTime));
            } else if (exclusiveLock.isLockExpired()) {
                exclusiveLockRepository.delete(exclusiveLock);
                return createNewLock(lockOwner, lockType, lockExpirationDateTime);
            } else {
                return Optional.empty();
            }
        } else {
            log.debug("No lock found for lockOwner: {} and lockType: {}, obtaining a new one.", lockOwner, lockType);
            return createNewLock(lockOwner, lockType, lockExpirationDateTime);
        }
    }

    private Optional<ExclusiveLock> createNewLock(@Size(max = 100) String lockOwner, @Size(max = 30) String lockType, LocalDateTime lockExpirationDateTime) {
        int rowCount = exclusiveLockRepository.insertNewLock(lockType, lockOwner, lockExpirationDateTime);
        return (rowCount > 0) ? exclusiveLockRepository.findByLockTypeAndLockedOwner(lockType, lockOwner) : Optional.empty();
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void releaseLock(ExclusiveLock exclusiveLock) {
        if (exclusiveLock != null) {
            this.exclusiveLockRepository.delete(exclusiveLock);
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void releaseLock(Optional<ExclusiveLock> exclusiveLock) {
        if ((exclusiveLock != null) && (exclusiveLock.isPresent())) {
            releaseLock(exclusiveLock.get());
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ExclusiveLock renewLock(ExclusiveLock exclusiveLock) {
        return renewLock(exclusiveLock, LocalDateTime.now().plusMinutes(DEFAULT_LOCK_DURATION_IN_MINUTES));
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ExclusiveLock renewLock(ExclusiveLock exclusiveLock, int lockDurationInMinutes) {
        return renewLock(exclusiveLock, LocalDateTime.now().plusMinutes(lockDurationInMinutes));
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ExclusiveLock renewLock(ExclusiveLock exclusiveLock, LocalDateTime newLockExpirationDateTime) {
        exclusiveLock.setLockExpirationDateTime(newLockExpirationDateTime);
        return this.exclusiveLockRepository.save(exclusiveLock);
    }

    public boolean recentLockExists(String lockType) {
        return this.exclusiveLockRepository.findByLockTypeAndLockExpirationDateTimeGreaterThan(lockType, LocalDateTime.now()).isPresent();
    }

    private String getLockOwner() throws UnknownHostException {
        if (hostname == null) {
            hostname = InetAddress.getLocalHost().getHostName();
        }
        return hostname + "_" + Thread.currentThread().getName();
    }

}
