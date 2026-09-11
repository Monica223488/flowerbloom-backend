package com.flowerbloom.backend.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class INaturalistService {

    private final RestClient restClient;

    public INaturalistService(){
        this.restClient = RestClient.builder()
                .baseUrl("https://api.inaturalist.org/v1/")
                .build();
    }

    public Integer getTaxonId(String scientificName) {
        Map response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/taxa")
                        .queryParam("q", scientificName)
                        .build())
                .retrieve()
                .body(Map.class);

        List<Map<String, Object>> results =
                (List<Map<String, Object>>) response.get("results");

        for (Map<String, Object> result : results) {
            if ("species".equals(result.get("rank"))
                    && scientificName.equals(result.get("name"))) {
                return ((Number) result.get("id")).intValue();
            }
        }
        return null;
    }
}
