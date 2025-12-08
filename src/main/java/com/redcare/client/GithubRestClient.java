package com.redcare.client;

import com.redcare.client.response.GithubResponse;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GithubRestClient {

    private final RestClient restClient;

    public GithubRestClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://api.github.com/search").build();
    }

    public GithubResponse findRepositories(String language, String createdAfter) {
        String q = null;

        if (StringUtils.isNotBlank(language) && StringUtils.isNotBlank(createdAfter)) {
            q = "language:" + language + "+created:>" + createdAfter;
        } else if (StringUtils.isNotBlank(language)) {
            q = "language:" + language;
        } else if (StringUtils.isNotBlank(createdAfter)) {
            q = "created:>" + createdAfter;
        }

        String rawUrl = "/repositories" + (q != null ? "?q=" + q : "");

        return restClient.get()
                .uri(rawUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(GithubResponse.class);
    }
}
