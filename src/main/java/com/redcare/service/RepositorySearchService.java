package com.redcare.service;

import com.redcare.calculator.ScoreCalculator;
import com.redcare.client.GithubRestClient;
import com.redcare.client.response.GithubItemResponse;
import com.redcare.client.response.GithubResponse;
import com.redcare.rest.respose.ItemResponse;
import com.redcare.rest.respose.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepositorySearchService {

    private final GithubRestClient client;
    private final ScoreCalculator calculator;

    public Response searchRepository(String language, String createdAfter) {
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
}
