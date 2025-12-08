package com.redcare.rest;

import com.redcare.rest.respose.Response;
import com.redcare.service.RepositorySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
@RequestMapping(value = "search", produces = "application/json")
public class RestController {

    private final RepositorySearchService repositorySearchService;

    @GetMapping("/repositories")
    public Response searchRepositories(@RequestParam(required = false) String language,
                                       @RequestParam(required = false) String createdAfter) {
        return repositorySearchService.searchRepository(language, createdAfter);
    }

}
