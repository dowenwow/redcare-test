package com.redcare.service;

import com.redcare.calculator.ScoreCalculator;
import com.redcare.client.GithubRestClient;
import com.redcare.client.response.GithubResponse;
import com.redcare.exception.InvalidCreatedAfterException;
import com.redcare.rest.respose.Response;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchRepositoryService {

    private final GithubRestClient client;
    private final ScoreCalculator calculator;

    public Response searchRepository(String language, String createdAfter) {
        log.info("Searching for repositories, language: {}, createdAfter: {}", language, createdAfter);
        validateCreatedAfter(createdAfter);
        GithubResponse githubResponse = client.findRepositories(language, createdAfter);
        log.debug("Github response: {}", githubResponse);
        return new Response(githubResponse.totalCount(), githubResponse.items().stream().map(this::map).toList());
    }

    private Response.Item map(GithubResponse.Item response) {
        return new Response.Item(
                response.name(),
                response.url(),
                response.language(),
                response.stargazersCount(),
                response.forks(),
                calculator.calculateScore(response.stargazersCount(), response.forks(), response.updatedAt())
        );
    }

    private void validateCreatedAfter(String createdAfter) {
        if (StringUtils.isNotBlank(createdAfter) && !createdAfter.matches("\\d{4}-\\d{2}-\\d{2}")) {
            log.error("Invalid created after date: {}", createdAfter);
            throw new InvalidCreatedAfterException("createdAfter must match format yyyy-MM-dd");
        }
    }
}
