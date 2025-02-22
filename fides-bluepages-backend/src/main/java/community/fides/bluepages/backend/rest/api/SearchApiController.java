package community.fides.bluepages.backend.rest.api;

import community.fides.bluepages.backend.domain.Did;
import community.fides.bluepages.backend.rest.api.dto.DidDto;
import community.fides.bluepages.backend.rest.api.form.DidForm;
import community.fides.bluepages.backend.rest.api.mapper.RestDidMapper;
import community.fides.bluepages.backend.service.DidDocumentService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/public/did")
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("permitAll()")

@OpenAPIDefinition(tags = @Tag(name = "Did API", description = "Search in the catalog"),
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Did API",
                version = "1.0",
                description = "API to search a Did in or add a Did to the Blue Pages",
                contact = @io.swagger.v3.oas.annotations.info.Contact(
                        url = "https://blue-pages.fides.community",
                        name = "Fides Blue Pages",
                        email = "catalog@fides.community"
                )
        )

)
@Tag(name = "Did API", description = "Search a Did in or add a Did to the Blue Pages")
public class SearchApiController {
    private final DidDocumentService didDocumentService;
    private final RestDidMapper didMapper;

    @Operation(summary = "Find all dids based on the query parameters.")
    @GetMapping
    public PagedModel<DidDto> find(@RequestParam(value = "q", required = false, defaultValue = "") String searchText,
                                   @ParameterObject @PageableDefault(value = 10) Pageable pageable) {
        final Page<Did> items = didDocumentService.searchDids(searchText, pageable);
        return new PagedModel<>(items.map(didMapper::from));
    }

    @Operation(summary = "Add a Did to the Blue pages.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received the request. The Did is queued for processing. When the Did is processed successfully the Did will appear in the Blue Pages.")

    })
    @PostMapping
    public void add(@RequestBody DidForm form) {
        didDocumentService.addWhenNotExists(form.getDid());
    }

}
