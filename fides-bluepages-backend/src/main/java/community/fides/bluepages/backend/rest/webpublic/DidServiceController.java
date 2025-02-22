package community.fides.bluepages.backend.rest.webpublic;

import community.fides.bluepages.backend.service.DidServiceVerifierService;
import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatusResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/webpublic/did/{didExternalKey}/service")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
@Slf4j
public class DidServiceController {

    private final DidServiceVerifierService didServiceVerifierService;

    @GetMapping("/{serviceExternalKey}/verify")
    public VerifiablePresentationStatusResponse verify(@PathVariable String didExternalKey, @PathVariable String serviceExternalKey) {
        return didServiceVerifierService.validate(didExternalKey, serviceExternalKey);
    }


}
