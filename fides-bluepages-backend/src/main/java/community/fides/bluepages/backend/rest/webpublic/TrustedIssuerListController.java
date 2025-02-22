package community.fides.bluepages.backend.rest.webpublic;

import community.fides.bluepages.backend.service.organizationalwallet.TrustedIssuerListClient;
import community.fides.bluepages.backend.service.organizationalwallet.dto.TrustedIssuerListDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/webpublic/trustedissuerlist")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
@Slf4j
public class TrustedIssuerListController {

    private final TrustedIssuerListClient trustedIssuerListClient;


    @GetMapping()
    public List<TrustedIssuerListDto> findAll() {
        return trustedIssuerListClient.getTrustedIssuers();
    }


}
