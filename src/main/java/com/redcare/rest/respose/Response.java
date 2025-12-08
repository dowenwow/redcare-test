package com.redcare.rest.respose;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Response(
        @JsonProperty("total_count")
        int totalCount,
        List<ItemResponse> items
) {

}
