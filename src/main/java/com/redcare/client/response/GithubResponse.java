package com.redcare.client.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubResponse(
        @JsonProperty("total_count")
        int totalCount,
        List<GithubItemResponse> items
) {
}
