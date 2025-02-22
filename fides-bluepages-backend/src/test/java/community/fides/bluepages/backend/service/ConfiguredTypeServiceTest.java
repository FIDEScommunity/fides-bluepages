package community.fides.bluepages.backend.service;

import community.fides.bluepages.backend.configuration.DisplayConfig;
import community.fides.bluepages.backend.domain.Credential;
import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.domain.DidService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ConfiguredTypeServiceTest {

    @InjectMocks
    private ConfiguredTypeService sut;

    @Spy
    private DisplayConfig displayConfig;

    @BeforeEach
    void setUp() {
        displayConfig.setCredentials(Map.of(
                "credentialType1", mock(DisplayConfig.CredentialConfig.class),
                "credentialType2", mock(DisplayConfig.CredentialConfig.class))
        );
        displayConfig.setServices(Map.of(
                "serviceType1", mock(DisplayConfig.ServiceConfig.class),
                "serviceType2", mock(DisplayConfig.ServiceConfig.class))
        );
    }


    @Test
    void testIsAllowedCredential() {
        assertThat(sut.isAllowedCredential(mockCredential("credentialType1"))).isTrue();
        assertThat(sut.isAllowedCredential(mockCredential("credentialType2"))).isTrue();
        assertThat(sut.isAllowedCredential(mockCredential("credentialType3"))).isFalse();
    }

    @Test
    void testIsAllowedService() {
        assertThat(sut.isAllowedService(mockService("serviceType1"))).isTrue();
        assertThat(sut.isAllowedService(mockService("serviceType2"))).isTrue();
        assertThat(sut.isAllowedService(mockService("serviceType3"))).isFalse();
    }


    @Test
    void testRemoveNotAllowedTypes() {
        // Arrange
        final Did testDid = Did.builder()
                .did("did:qqq:abc")
                .services(new ArrayList<>(List.of(
                        mockService("serviceType1", List.of(
                                mockCredential("credentialType1"),
                                mockCredential("credentialType2")
                        )),
                        mockService("LinkedVerifiablePresentation", List.of(
                                mockCredential("credentialType1"),
                                mockCredential("NotAllowedType")
                        )),
                        mockService("NotAllowedType", List.of(
                                mockCredential("credentialType1"),
                                mockCredential("credentialType3")
                        ))
                )))
                .build();

        // Act
        final Did updatedDid = sut.removeNotAllowedTypes(testDid);

        // Assert
        assertThat(updatedDid.getServices()).hasSize(2);
        assertThat(updatedDid.getServices().get(0).getServiceType()).isEqualTo("serviceType1");
        assertThat(updatedDid.getServices().get(0).getCredentials()).hasSize(2);
        assertThat(updatedDid.getServices().get(1).getServiceType()).isEqualTo("LinkedVerifiablePresentation");
        assertThat(updatedDid.getServices().get(1).getCredentials()).hasSize(1);
    }

    @Test
    void testContainsAtLeastOneRequiredServiceOrCredential() {
        // Arrange
        final Did testDid = Did.builder()
                .did("did:qqq:abc")
                .services(List.of(
                        mockService("serviceType1", List.of(
                                mockCredential("credentialType1"),
                                mockCredential("credentialType2")
                        )),
                        mockService("LinkedVerifiablePresentation", List.of(
                                mockCredential("credentialType1"),
                                mockCredential("NotAllowedType")
                        )),
                        mockService("NotAllowedType", List.of(
                                mockCredential("credentialType1"),
                                mockCredential("credentialType3")
                        ))
                ))
                .build();

        // Act
        final boolean result = sut.containsAtLeastOneRequiredServiceOrCredential(testDid);

        // Assert
        assertThat(result).isTrue();
    }


    private Credential mockCredential(String type) {
        return Credential.builder()
                .type(type)
                .build();
    }

    private DidService mockService(String type) {
        return DidService.builder()
                .serviceType(type)
                .build();
    }

    private DidService mockService(String type, List<Credential> credentials) {
        return DidService.builder()
                .serviceType(type)
                .credentials(credentials)
                .build();
    }
}
