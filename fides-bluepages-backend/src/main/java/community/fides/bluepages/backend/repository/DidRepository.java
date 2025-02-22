package community.fides.bluepages.backend.repository;

import community.fides.bluepages.backend.domain.CredentialAttribute_;
import community.fides.bluepages.backend.domain.Credential_;
import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.domain.DidService_;
import community.fides.bluepages.backend.domain.Did_;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import static community.fides.bluepages.backend.repository.CriteriaApiUtils.join;


public interface DidRepository extends JpaRepository<Did, Long>, JpaSpecificationExecutor<Did> {

    @Query("""
            select d.id from Did d
                where MOD(d.id, :totalPartitions) = :partition
                and (
                    ((d.rawDataLastUpdatedAt > :recentlyChangedPointInTime) and (d.lastCrawledAt < :recentlyChangedCrawlTimestamp)) 
                    or (d.lastCrawledAt < :nonRecentlyChangedCrawlTimestamp)
                    or (d.lastCrawledAt is null)
                )
            """)
    List<Long> findDidsToCrawl(Integer partition, Integer totalPartitions, LocalDateTime recentlyChangedPointInTime, LocalDateTime recentlyChangedCrawlTimestamp, LocalDateTime nonRecentlyChangedCrawlTimestamp);

    @Query("SELECT d FROM Did d WHERE d.services IS EMPTY")
    Page<Did> findAllByServicesWithAttributesValues(String attributeValue, Pageable pageable);


    default Specification<Did> containsDid(String searchText) {
        return (did, cq, cb) -> cb.like(cb.upper(did.get("did")), "%" + searchText.toUpperCase(Locale.ROOT) + "%");
    }

    default Specification<Did> meetsTypeRequirements(boolean meetsTypeRequirements) {
        return (did, cq, cb) -> cb.equal(did.get(Did_.MEETS_TYPE_REQUIREMENTS), meetsTypeRequirements);
    }

    default Specification<Did> containsCredentialType(String searchText) {
        return (did, cq, cb) -> cb.like(cb.upper(
                join(
                        join(did, Did_.SERVICES, JoinType.LEFT),
                        DidService_.CREDENTIALS, JoinType.LEFT
                ).get(Credential_.TYPE)
        ), "%" + searchText.toUpperCase(Locale.ROOT) + "%");
    }

    default Specification<Did> containsCredentialAttributeValue(String searchText) {
        return (did, cq, cb) -> cb.like(cb.upper(
                                                join(
                                                        join(
                                                                join(
                                                                        did, Did_.SERVICES, JoinType.LEFT
                                                                ),
                                                                DidService_.CREDENTIALS, JoinType.LEFT
                                                        ),
                                                        Credential_.ATTRIBUTES, JoinType.LEFT
                                                ).get(CredentialAttribute_.VALUE)),
                                        "%" + searchText.toUpperCase(Locale.ROOT) + "%");
    }

    default Specification<Did> containsCredentialAttributeKey(String searchText) {
        return (did, cq, cb) -> cb.like(cb.upper(
                                                join(
                                                        join(
                                                                join(did, Did_.SERVICES, JoinType.LEFT),
                                                                DidService_.CREDENTIALS, JoinType.LEFT
                                                        ),
                                                        Credential_.ATTRIBUTES, JoinType.LEFT
                                                ).get(CredentialAttribute_.KEY)),
                                        "%" + searchText.toUpperCase(Locale.ROOT) + "%");
    }

    default Specification<Did> containsServiceId(String searchText) {
        return (did, cq, cb) -> cb.like(cb.upper(join(did, Did_.SERVICES, JoinType.LEFT).get(DidService_.SERVICE_ID)), "%" + searchText.toUpperCase(Locale.ROOT) + "%");
    }

    default Specification<Did> containsServiceType(String searchText) {
        return (did, cq, cb) -> cb.like(cb.upper(join(did, Did_.SERVICES, JoinType.LEFT).get(DidService_.SERVICE_TYPE)), "%" + searchText.toUpperCase(Locale.ROOT) + "%");
    }

    default Specification<Did> containsServiceTypeJson(String searchText) {
        return (did, cq, cb) -> cb.like(cb.upper(join(did, Did_.SERVICES, JoinType.LEFT).get(DidService_.SERVICE_TYPE_JSON)), "%" + searchText.toUpperCase(Locale.ROOT) + "%");
    }

    default Specification<Did> containsServiceEndpoint(String searchText) {
        return (did, cq, cb) -> cb.like(cb.upper(join(did, Did_.SERVICES, JoinType.LEFT).get(DidService_.SERVICE_ENDPOINT)), "%" + searchText.toUpperCase(Locale.ROOT) + "%");
    }

    default Specification<Did> containsServiceEndpointJson(String searchText) {
        return (did, cq, cb) -> cb.like(cb.upper(join(did, Did_.SERVICES, JoinType.LEFT).get(DidService_.SERVICE_ENDPOINT_JSON)), "%" + searchText.toUpperCase(Locale.ROOT) + "%");
    }


    default Specification<Did> allOfAnd(List<Specification<Did>> specifications) {
        return (did, cq, cb) -> {
            cq.distinct(true);
            Predicate[] predicates = specifications.stream().map(specification -> specification.toPredicate(did, cq, cb))
                    .toArray(Predicate[]::new);
            return cb.and(predicates);
        };
    }

    default Specification<Did> allOfOr(List<Specification<Did>> specifications) {
        return (did, cq, cb) -> {
            cq.distinct(true);
            Predicate[] predicates = specifications.stream().map(specification -> specification.toPredicate(did, cq, cb))
                    .toArray(Predicate[]::new);
            return cb.or(predicates);
        };
    }

    Optional<Did> findDidByDid(@NotNull String did);

    Optional<Did> findByExternalKey(String didExternalKey);

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    default Did saveAndCommit(Did entity) {
        return save(entity);
    }

    @Modifying
    @Query(value = """
            INSERT IGNORE INTO did(external_key, did, registered_at) 
                VALUES (:externalKey, :did, :registeredAt) 
            """,
            nativeQuery = true)
    void addWhenNotExists(String externalKey, String did, LocalDateTime registeredAt);
}
