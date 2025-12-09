package com.redcare.client.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubResponse(
        @JsonProperty("total_count")
        int totalCount,
        List<Item> items
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Item(
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
}
