package com.redcare.client;

import com.redcare.client.response.GithubResponse;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class GithubRestClient {

    private final RestClient restClient;

    public GithubResponse findRepositories(String language, String createdAfter) {
        log.info("Searching for repositories, language: {}, createdAfter: {}", language, createdAfter);
        String q = null;

        if (StringUtils.isNotBlank(language) && StringUtils.isNotBlank(createdAfter)) {
            q = "language:" + language + "+created:>" + createdAfter;
        } else if (StringUtils.isNotBlank(language)) {
            q = "language:" + language;
        } else if (StringUtils.isNotBlank(createdAfter)) {
            q = "created:>" + createdAfter;
        }

        String rawUrl = "/repositories" + (q != null ? "?q=" + q : "");
        log.debug("Raw URL: {}", rawUrl);

        return restClient.get()
                .uri(rawUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is2xxSuccessful, (request, response) -> {
                    log.info("Received 2xx status code, code: {}", response.getStatusCode());
                }).onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    log.error("Received 4xx status code, code: {}", response.getStatusCode());
                }).onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    log.error("Received 5xx status code, code: {}", response.getStatusCode());
                })
                .body(GithubResponse.class);
    }
}
