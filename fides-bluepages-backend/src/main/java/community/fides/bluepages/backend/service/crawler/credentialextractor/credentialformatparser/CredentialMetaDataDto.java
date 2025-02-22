package community.fides.bluepages.backend.service.crawler.credentialextractor.credentialformatparser;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CredentialMetaDataDto {
    String type;
    String issuerDid;
    String subjectDid;
}
