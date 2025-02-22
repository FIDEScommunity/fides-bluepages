package community.fides.bluepages.backend.service.crawler.credentialextractor.credentialformatparser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class SdJwtParserTest {

    private SdJwtParser sut = new SdJwtParser();


    @SneakyThrows
    @Test
    void givenValidSdJwt_whenCanParse_thenExpectTrue() {
        // Arrange
        final JsonNode jsonNode = new ObjectMapper().readTree(sdJwtJson);

        // Act & assert
        assertThat(sut.canParse(jsonNode)).isTrue();
    }

    @SneakyThrows
    @Test
    void givenValidVcJwt_whenCanParse_thenExpectFalse() {
        // Arrange
        final JsonNode jsonNode = new ObjectMapper().readTree(vcJwtJson);

        // Act & assert
        assertThat(sut.canParse(jsonNode)).isFalse();
    }

    @SneakyThrows
    @Test
    void givenValidSdJwt_whenParse_thenExpectCorrectDto() {
        // Arrange
        final JsonNode jsonNode = new ObjectMapper().readTree(sdJwtJson);

        // Act
        final Optional<CredentialMetaDataDto> credentialMetaDataDto = sut.parse(jsonNode);

        // Assert
        assertThat(credentialMetaDataDto).isPresent();
        assertThat(credentialMetaDataDto.get().getType()).isEqualTo("KVKRegistration_sd_jwt");
        assertThat(credentialMetaDataDto.get().issuerDid).isEqualTo("did:web:mijnkvk.acc.credenco.com:did:df63abc0-2cc9-49e5-b3f8-42a824ceb9a7-sd-jwt");
        assertThat(credentialMetaDataDto.get().subjectDid).isEqualTo("did:web:wallet.fat1.credenco.com:did:8c12e948-f9b4-4788-b606-2585cc279e89");

    }


    String sdJwtJson = """
            {
              "vct": "KVKRegistration_sd_jwt",
              "kvkNummer": "36741298",
              "naam": "FurniTrade B.V.",
              "rechtsvorm": "B.V.",
              "startdatum": "2021-08-01",
              "id": "urn:uuid:d5d4888f-1714-4e27-a9a7-e1b084f29cbe",
              "iss": "did:web:mijnkvk.acc.credenco.com:did:df63abc0-2cc9-49e5-b3f8-42a824ceb9a7-sd-jwt",
              "sub": "did:web:wallet.fat1.credenco.com:did:8c12e948-f9b4-4788-b606-2585cc279e89",
              "iat": 1739126207,
              "nbf": 1739126207,
              "exp": 1770662207,
              "_sd": [
                "3w3UznyWKy6K_Y_EeEdhFVMkMBfSp1qfIbSU7VVI99o"
              ]
            }
            """;

    String vcJwtJson = """
            {
                "iss": "did:web:mijnkvk.acc.credenco.com:did:df63abc0-2cc9-49e5-b3f8-42a824ceb9a7",
                "sub": "did:web:wallet.fat1.credenco.com:did:8c12e948-f9b4-4788-b606-2585cc279e89",
                "vc": {
                  "@context": [
                    "https://www.w3.org/2018/credentials/v1",
                    "https://www.w3.org/2018/credentials/examples/v1"
                  ],
                  "id": "urn:uuid:11b55d38-5db1-49f1-a338-c450d0286d2d",
                  "type": [
                    "VerifiableCredential",
                    "VerifiableAttestation",
                    "KVKRegistration"
                  ],
                  "issuer": "did:web:mijnkvk.acc.credenco.com:did:df63abc0-2cc9-49e5-b3f8-42a824ceb9a7-vc-jwt",
                  "issued": "2025-02-09T13:32:06.244560515Z",
                  "validFrom": "2025-02-09T13:32:06.244560515Z",
                  "issuanceDate": "2025-02-09T13:32:06.244560515Z",
                  "credentialSubject": {
                    "id": "did:web:wallet.fat1.credenco.com:did:8c12e948-f9b4-4788-b606-2585cc279e89",
                    "kvkNummer": "24129876",
                    "naam": "DutchCraft Furnishing",
                    "rechtsvorm": "Besloten Vennootschap",
                    "startdatum": "2022-07-01",
                    "einddatum": ""
                  },
                  "termsOfUse": [
                    {
                      "id": "https://api-pilot.ebsi.eu/trusted-issuers-registry/v5/issuers/did:ebsi:zivtiw8mjYWEV8vDWodTKoj/attributes/e9c28a971ce537d5681b159b05807850163fad7f391c5271d9c5be5688ac8f68",
                      "type": "IssuanceCertificate"
                    }
                  ],
                  "credentialSchema": {
                    "id": "https://api-pilot.ebsi.eu/trusted-schemas-registry/v3/schemas/0xd2204647818f9c0c93f64b70f1c892ea2a8e0a747ceaa2f77373b41996bb764d",
                    "type": "FullJsonSchemaValidator2021"
                  },
                  "credentialStatus": {
                    "id": "https://wallet.acc.credenco.com/api/status/df803dd4-32c6-4a69-afe5-d807c6701ed7/1#61065",
                    "type": "StatusList2021Entry",
                    "statusPurpose": "revocation",
                    "statusListIndex": 61065,
                    "statusListCredential": "https://wallet.acc.credenco.com/api/status/df803dd4-32c6-4a69-afe5-d807c6701ed7/1"
                  }
                },
                "jti": "urn:uuid:11b55d38-5db1-49f1-a338-c450d0286d2d",
                "iat": 1739107926,
                "nbf": 1739107926
              }
            """;


}
