package community.fides.bluepages.backend.rest.webpublic;

import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.repository.DidRepository;
import community.fides.bluepages.backend.rest.webpublic.dto.DidDto;
import community.fides.bluepages.backend.rest.webpublic.dto.TrustedIssuerListDto;
import community.fides.bluepages.backend.rest.webpublic.mapper.DidMapper;
import community.fides.bluepages.backend.service.organizationalwallet.TrustedIssuerListClient;
import community.fides.bluepages.backend.service.organizationalwallet.dto.TrustedIssuerListOwDto;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/webpublic/trustedissuerlist")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
@Slf4j
public class TrustedIssuerListController {

    private final TrustedIssuerListClient trustedIssuerListClient;
    private final DidRepository didRepository;
    private final DidMapper didMapper;

    @GetMapping()
    public List<TrustedIssuerListDto> findAll(@RequestParam(value = "locale", required = false, defaultValue = "en-EN") String locale) {
        return trustedIssuerListClient.getTrustedIssuers().stream()
                .map(trustedIssuerListDto -> toDto(trustedIssuerListDto, locale))
                .toList();
    }

    private TrustedIssuerListDto toDto(final TrustedIssuerListOwDto trustedIssuerListDto, String locale) {
        final List<DidDto> didDtoList = trustedIssuerListDto.getTrustedDids().stream()
                .map(didDto -> {
                    final Optional<Did> didByDid = didRepository.findDidByDid(didDto.getDid());
                    if (didByDid.isEmpty()) {
                        return DidDto.builder().did(didDto.getDid()).build();
                    } else {
                        return didMapper.globalInfoFrom(didByDid.get(), locale, null);
                    }
                })
                .toList();
        return TrustedIssuerListDto.builder()
                .credentialType(trustedIssuerListDto.getCredentialType())
                .trustedDids(didDtoList)
                .build();
    }
}
