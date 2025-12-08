package com.redcare.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubItemResponse(
        String name,
        String url,
        String language,
        @JsonProperty("stargazers_count")
        int stargazersCount,
        int forks,
        @JsonProperty("updated_at")
        OffsetDateTime updatedAt
) {

}