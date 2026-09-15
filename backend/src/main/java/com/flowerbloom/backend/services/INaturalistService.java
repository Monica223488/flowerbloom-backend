package com.flowerbloom.backend.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.LinkedHashMap;
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

    public Map<Integer, Integer> getFloweringHistogram(Integer taxonId) {
        Map response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/observations/histogram")
                        .queryParam("taxon_id", taxonId)
                        .queryParam("term_id", 12)
                        .queryParam("term_value_id", 13)
                        .queryParam("place_id", 7506)
                        .build())
                .retrieve()
                .body(Map.class);

        Map<String, Object> results =
                (Map<String, Object>) response.get("results");
        Map<String, Integer> monthOfYear =
                (Map<String, Integer>) results.get("month_of_year");
        Map<Integer, Integer> floweringHistogram = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : monthOfYear.entrySet()) {
            floweringHistogram.put(
                    Integer.parseInt(entry.getKey()),
                    entry.getValue()
            );
        }

        return floweringHistogram;
    }
}
