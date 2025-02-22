package community.fides.bluepages.backend.rest.webpublic;

import java.nio.charset.Charset;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/webpublic/schema")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
@Slf4j
public class JsonSchemaController {

    private final ClassPathResource organizationSchema = new ClassPathResource("json-schemas/organization.schema.json");

    @SneakyThrows
    @GetMapping(value = "/organization", produces = "application/json")
    public String organization() {
        return organizationSchema.getContentAsString(Charset.defaultCharset());
    }


}
