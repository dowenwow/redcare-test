package com.redcare.rest;

import com.redcare.rest.respose.Response;
import com.redcare.service.SearchRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "search", produces = "application/json")
public class RepositorySearchRestController {

    private final SearchRepositoryService service;

    @GetMapping("/repositories")
    public Response searchRepositories(@RequestParam(required = false) String language,
                                       @RequestParam(required = false) String createdAfter) {
        log.info("Received request to search repositories, language: {}, createdAfter: {}", language, createdAfter);
        Response response = service.searchRepository(language, createdAfter);
        log.debug("Received response from search repositories service, response: {}", response);
        return response;
    }

}
