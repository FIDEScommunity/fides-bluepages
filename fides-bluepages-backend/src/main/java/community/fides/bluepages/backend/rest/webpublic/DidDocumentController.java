package community.fides.bluepages.backend.rest.webpublic;

import community.fides.bluepages.backend.configuration.error.ErrorCodeException;
import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.domain.DidService;
import community.fides.bluepages.backend.repository.DidRepository;
import community.fides.bluepages.backend.rest.webpublic.dto.DidDto;
import community.fides.bluepages.backend.rest.webpublic.form.DidForm;
import community.fides.bluepages.backend.rest.webpublic.mapper.DidMapper;
import community.fides.bluepages.backend.service.DidDocumentService;
import community.fides.bluepages.backend.service.DidServiceVerifierService;
import community.fides.bluepages.backend.service.DidSortService;
import community.fides.bluepages.backend.service.DidStoreService;
import community.fides.bluepages.backend.service.crawler.Crawler;
import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatusResponse;
import jakarta.transaction.Transactional;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static community.fides.bluepages.backend.configuration.error.ErrorCodeException.ERR_RESOURCE_NOT_FOUND;

@RestController()
@RequestMapping("/api/webpublic/did")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
@Slf4j
public class DidDocumentController {

    private final DidRepository didRepository;
    private final DidDocumentService didDocumentService;
    private final DidMapper didMapper;
    private final Crawler crawler;
    private final DidStoreService didStoreService;
    private final DidSortService didSortService;
    private final DidServiceVerifierService didServiceVerifierService;

    @GetMapping()
    public Page<DidDto> find(@RequestParam(value = "q", required = false, defaultValue = "") String searchText,
                             @RequestParam(value = "locale", required = false, defaultValue = "en-EN") String locale,
                             Pageable pageable) {
        final Page<Did> items = didDocumentService.searchDids(searchText, pageable);
        return items.map(did -> didMapper.from(did, locale));
    }


    @GetMapping("/{didExternalKey}")
    @Transactional
    public DidDto get(@PathVariable String didExternalKey, @RequestParam(value = "locale", required = false, defaultValue = "en-EN") String locale) {
        final Did didFromDb = didRepository.findByExternalKey(didExternalKey).orElseThrow(() -> new IllegalArgumentException("Did not found"));
        var didDto = didMapper.from(didSortService.sortDid(didFromDb), locale);
        return enrichWithIssuerDids(didDto, locale);
    }

    @GetMapping("/{didExternalKey}/validations")
    @Transactional
    public DidDto getWithValidations(@PathVariable String didExternalKey, @RequestParam(value = "locale", required = false, defaultValue = "en-EN") String locale) {
        final Did didFromDb = didRepository.findByExternalKey(didExternalKey).orElseThrow(() -> new IllegalArgumentException("Did not found"));
        final Did crawledDidDocument = crawler.crawlDidDocument(didFromDb.getDid()).orElseThrow(() -> new IllegalArgumentException("Did not found"));
        final Did updatedDid = didStoreService.update(didFromDb.getId(), crawledDidDocument);
        final Map<DidService, VerifiablePresentationStatusResponse> validationResults = didServiceVerifierService.validate(updatedDid);

        final DidDto didDto = didMapper.from(didSortService.sortDid(updatedDid), locale, validationResults);
        return enrichWithIssuerDids(didDto, locale);
    }

    private DidDto enrichWithIssuerDids(final DidDto didDto, String locale) {
        didDto.getServices().stream()
                .flatMap(didServiceDto -> didServiceDto.getCredentials().stream())
                .forEach(credentialDto -> {
                    didRepository.findDidByDid(credentialDto.getIssuerDidId()).ifPresent(did -> {
                        credentialDto.setIssuerDid(didMapper.globalInfoFrom(did, locale, null));
                    });
                });
        return didDto;
    }

    @PostMapping("/preview")
    public DidDto preview(@RequestParam(value = "locale", required = false, defaultValue = "en-EN") String locale, @RequestBody DidForm didForm) {
        final Did crawledDidDocument = crawler.crawlDidDocument(didForm.getDid()).orElseThrow(() -> new ErrorCodeException(ERR_RESOURCE_NOT_FOUND, "Did not found"));
        final Map<DidService, VerifiablePresentationStatusResponse> validationResults = (didForm.isPerformValidations()) ? didServiceVerifierService.validate(crawledDidDocument) : Map.of();
        var didDto = didMapper.from(didSortService.sortDid(crawledDidDocument), locale, validationResults);
        return enrichWithIssuerDids(didDto, locale);
    }

    @PostMapping()
    public DidDto add(@RequestParam(value = "locale", required = false, defaultValue = "en-EN") String locale, @RequestBody DidForm didForm) {
        final Optional<Did> crawledDidDocument = crawler.crawlDidDocument(didForm.getDid());
        crawledDidDocument.ifPresent(didStoreService::add);
        final Did did = crawledDidDocument.orElseThrow(() -> new ErrorCodeException(ERR_RESOURCE_NOT_FOUND, "Did not found"));
        var didDto = didMapper.from(didSortService.sortDid(did), locale);
        return enrichWithIssuerDids(didDto, locale);
    }


}
