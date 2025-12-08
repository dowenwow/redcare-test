package com.redcare.service;

import com.redcare.calculator.ScoreCalculator;
import com.redcare.client.GithubRestClient;
import com.redcare.client.response.GithubItemResponse;
import com.redcare.client.response.GithubResponse;
import com.redcare.exception.InvalidCreatedAfterException;
import com.redcare.rest.respose.ItemResponse;
import com.redcare.rest.respose.Response;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@RequiredArgsConstructor
public class RepositorySearchService {

    private final GithubRestClient client;
    private final ScoreCalculator calculator;

    public Response searchRepository(String language, String createdAfter) {
        validateCreatedAfter(createdAfter);
        GithubResponse githubResponse = client.findRepositories(language, createdAfter);
        return new Response(githubResponse.totalCount(), githubResponse.items().stream().map(this::map).toList());
    }

    private ItemResponse map(GithubItemResponse response) {
        return new ItemResponse(
                response.name(),
                response.url(),
                response.language(),
                response.stargazersCount(),
                response.forks(),
                calculator.calculateScore(response.stargazersCount(), response.forks(), response.updatedAt())
        );
    }

    private void validateCreatedAfter(String createdAfter) {
        if (!StringUtils.isNotBlank(createdAfter) && !createdAfter.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new InvalidCreatedAfterException("createdAfter must match format yyyy-MM-dd");
        }
    }
}
