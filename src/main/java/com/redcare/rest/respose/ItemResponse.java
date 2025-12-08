package com.redcare.rest.respose;

public record ItemResponse (
        String name,
        String url,
        String language,
        int start,
        int forks,
        double score
){
}
