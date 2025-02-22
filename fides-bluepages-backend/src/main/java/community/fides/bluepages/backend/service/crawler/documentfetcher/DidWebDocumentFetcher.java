package community.fides.bluepages.backend.service.crawler.documentfetcher;

import community.fides.bluepages.backend.domain.Did;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DidWebDocumentFetcher implements DidDocumentFetcher {


    private final RestTemplate restTemplate;

    public DidWebDocumentFetcher(@Qualifier("didweb") final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public boolean canFetchRawData(final Did did) {
        if ((did == null) || (did.getDid() == null)) {
            return false;
        }
        return did.getDid().startsWith("did:web:");
    }

    @Override
    public byte[] fetchRawData(final Did did) {
        URI uri = buildRetrieveUrl(did);

        return restTemplate.getForObject(uri, byte[].class);
    }


    // Implement https://w3c-ccg.github.io/did-method-web/#read-resolve spec
    @SneakyThrows
    private URI buildRetrieveUrl(final Did did) {
        // Extract the method-specific identifier (removing "did:web:")
        String methodSpecificIdentifier = did.getDid().substring("did:web:".length());


        final String decodedUrl = URLDecoder.decode(methodSpecificIdentifier, StandardCharsets.UTF_8);
        // Replace ":" with "/"
        String url = decodedUrl.replace(":", "/");

        // Extract host and optional url
        boolean containsPort = decodedUrl.matches("^[^:]*:(\\d).*"); // Detect if there's a port

        // Decode colon in port (optional for clarity in URI handling)
        if (containsPort) {
            url = url.replaceFirst("/", ":"); // Percent decode for port colon
        }

        // Ensure proper HTTPS URI format
        String httpsUrl = "https://" + url;

        // Append /.well-known if no url is specified
        if (!url.contains("/")) {
            httpsUrl += "/.well-known";
        }

        // Append /did.json to complete the URL
        httpsUrl += "/did.json";

        // Create the URI from the constructed URL
        URI uri = URI.create(httpsUrl);
        return uri;
    }
}
