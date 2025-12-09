package com.redcare.rest.respose;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Response(
        @JsonProperty("total_count")
        int totalCount,
        List<Item> items
) {
    public record Item(String name,
                       String url,
                       String language,
                       int start,
                       int forks,
                       double score) {

    }
}
