package community.fides.bluepages.backend.service;

import community.fides.bluepages.backend.domain.Credential;
import community.fides.bluepages.backend.domain.CredentialAttribute;
import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.domain.DidService;
import community.fides.bluepages.backend.repository.DidRepository;
import community.fides.bluepages.backend.utils.EqualUtils;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DidStoreService {

    private final DidRepository didRepository;

    final JpaCollectionMerger<DidService, DidService> didServiceMerger = new JpaCollectionMerger<>();
    final JpaCollectionMerger<Credential, Credential> credentialMerger = new JpaCollectionMerger<>();
    final JpaCollectionMerger<CredentialAttribute, CredentialAttribute> credentialAttributeMerger = new JpaCollectionMerger<>();

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Did updateTxNew(long didId, Did newDid) {
        return update(didId, newDid);
    }

    @Transactional
    public Did update(long didId, Did newDid) {
        final Optional<Did> oldDidOptional = didRepository.findById(didId);
        if (oldDidOptional.isEmpty()) {
            return null;
        }
        final Did didToUpdate = oldDidOptional.get();
        final byte[] oldRawDataHash = didToUpdate.getRawDataHash();
        final byte[] newRawDataHash = hash(newDid.getRawData());
        didToUpdate.setDid(newDid.getDid());
        didToUpdate.setServices(mergeServices(didToUpdate, didToUpdate.getServices(), newDid.getServices()));
        didToUpdate.setLastCrawledAt(LocalDateTime.now());
        didToUpdate.setMeetsTypeRequirements(newDid.isMeetsTypeRequirements());
        if (!Arrays.equals(oldRawDataHash, newRawDataHash)) {
            didToUpdate.setRawData(newDid.getRawData());
            didToUpdate.setRawDataHash(newRawDataHash);
            log.info("Updated content for did {}, meetsTypeRequirements {}", didToUpdate.getDid(), didToUpdate.meetsTypeRequirements());
            didToUpdate.setRawDataLastUpdatedAt(LocalDateTime.now());
        }
        return didRepository.save(didToUpdate);
    }

    private List<DidService> mergeServices(final Did did, final List<DidService> oldServices, final List<DidService> newServices) {
        return didServiceMerger.buildJpaCollection(
                newServices, oldServices,
                (newService, currentService) -> EqualUtils.functionalEquals(newService.getId(), currentService.getId()),
                (newService, oldService) -> {
                    copy(newService, oldService);
                    return oldService;
                },

                (newService) -> {
                    var entity = new DidService();
                    entity.setExternalKey(UUID.randomUUID().toString());
                    copy(newService, entity);
                    entity.setDid(did);
                    return entity;
                });
    }

    private void copy(final DidService fromService, final DidService toService) {
        toService.setServiceId(fromService.getServiceId());
        toService.setServiceType(fromService.getServiceType());
        toService.setServiceTypeJson(fromService.getServiceTypeJson());
        toService.setServiceEndpoint(fromService.getServiceEndpoint());
        toService.setServiceEndpointJson(fromService.getServiceEndpointJson());
        toService.setCredentials(mergeCredentials(toService, toService.getCredentials(), fromService.getCredentials()));
    }

    private List<Credential> mergeCredentials(final DidService didService, final List<Credential> oldCredentials, final List<Credential> newCredentials) {
        return credentialMerger.buildJpaCollection(
                newCredentials, oldCredentials,
                (newCredential, currentCredential) -> EqualUtils.functionalEquals(newCredential.getId(), currentCredential.getId()),
                (newCredential, oldCredential) -> {
                    copy(newCredential, oldCredential);
                    return oldCredential;
                },

                (newCredential) -> {
                    var entity = new Credential();
                    copy(newCredential, entity);
                    entity.setDidService(didService);
                    return entity;
                });
    }

    private void copy(final Credential fromCredential, final Credential toCredential) {
        toCredential.setType(fromCredential.getType());
        toCredential.setIssuerDid(fromCredential.getIssuerDid());
        toCredential.setSubjectDid(fromCredential.getSubjectDid());
        toCredential.setStatus(fromCredential.getStatus());
        toCredential.setLastUpdated(fromCredential.getLastUpdated());
        toCredential.setAttributes(mergeCredentialAttributes(toCredential, toCredential.getAttributes(), fromCredential.getAttributes()));
    }

    private List<CredentialAttribute> mergeCredentialAttributes(final Credential credential, final List<CredentialAttribute> oldCredentialAttributes, final List<CredentialAttribute> newCredentialAttributes) {
        return credentialAttributeMerger.buildJpaCollection(
                newCredentialAttributes, oldCredentialAttributes,
                (newCredentialAttribute, currentCredentialAttribute) -> EqualUtils.functionalEquals(newCredentialAttribute.getId(), currentCredentialAttribute.getId()),
                (newCredentialAttribute, oldCredentialAttribute) -> {
                    copy(newCredentialAttribute, oldCredentialAttribute);
                    return oldCredentialAttribute;
                },

                (newCredentialAttribute) -> {
                    var entity = new CredentialAttribute();
                    copy(newCredentialAttribute, entity);
                    entity.setCredential(credential);
                    return entity;
                });
    }

    private void copy(final CredentialAttribute from, final CredentialAttribute to) {
        to.setKey(from.getKey());
        to.setValue(from.getValue());
        to.setValueText(from.getValueText());
    }

    @SneakyThrows
    private byte[] hash(byte[] data) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(data);
    }

    @Transactional
    public Did updateLastCrawledAt(final Long id, final LocalDateTime now) {
        final Optional<Did> did = didRepository.findById(id);
        if (did.isEmpty()) {
            return null;
        }
        did.get().setLastCrawledAt(now);
        return didRepository.save(did.get());
    }

    @Transactional
    public Did add(final Did did) {
        if (did.getId() != null) {
            return update(did.getId(), did);
        }

        final Optional<Did> existingDid = didRepository.findDidByDid(did.getDid());
        if (existingDid.isEmpty()) {
            did.setRegisteredAt(LocalDateTime.now());
            did.setExternalKey(UUID.randomUUID().toString());
            return didRepository.save(did);
        } else {
            return update(existingDid.get().getId(), did);
        }
    }
}
