package community.fides.bluepages.backend.configuration;

import community.fides.bluepages.backend.service.organizationalwallet.dto.TrustedIssuerListOwDto;
import community.fides.bluepages.backend.service.organizationalwallet.dto.VerifiablePresentationStatusResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

@Getter
@Setter
@ConfigurationProperties(prefix = "mock-organizational-wallet-config")
public class MockOrganizationalWalletConfig {
    private List<TrustedIssuerListOwDto> trustedIssuerList;
    private VerifiablePresentationStatusResponse verifiablePresentationStatus;
}

