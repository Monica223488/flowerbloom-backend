package com.flowerbloom.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrefleService {

    @Value("${trefle.api.key}")
    private String trefleApiKey;

    private final RestClient restClient;

    public TrefleService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://trefle.io/api/v1")
                .build();
    }

    public String getPlants(){
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/plants")
                        .queryParam("token", trefleApiKey)
                        .build())
                .retrieve()
                .body(String.class);
    }

    public List<Integer> getBloomMonths(String scientificName) {
        Map response = this.restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/species")
                        .queryParam("token", trefleApiKey)
                        .queryParam("q", scientificName)
                        .build())
                .retrieve()
                .body(Map.class);

        List<Map<String, Object>> species =
                (List<Map<String, Object>>) response.get("data");
        for (Map<String, Object> speciesMap : species) {
            if (scientificName.equals(speciesMap.get("scientific_name"))) {
                String slug = (String) speciesMap.get("slug");
                Map response2 = this.restClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/species/" + slug)
                                .queryParam("token", trefleApiKey)
                                .build())
                        .retrieve()
                        .body(Map.class);
                Map<String, Object> data =
                        (Map<String, Object>) response2.get("data");
                Map<String, Object> growth =
                        (Map<String, Object>) data.get("growth");
                List<String> bloomMonths =
                        (List<String>) growth.get("bloom_months");

                Map<String, Integer> monthNumbers = new HashMap<>();
                monthNumbers.put("jan", 1);
                monthNumbers.put("feb", 2);
                monthNumbers.put("mar", 3);
                monthNumbers.put("apr", 4);
                monthNumbers.put("may", 5);
                monthNumbers.put("jun", 6);
                monthNumbers.put("jul", 7);
                monthNumbers.put("aug", 8);
                monthNumbers.put("sep", 9);
                monthNumbers.put("oct", 10);
                monthNumbers.put("nov", 11);
                monthNumbers.put("dec", 12);

                List<Integer> bloomMonthNumbers = new ArrayList<>();
                for (String month : bloomMonths) {
                    Integer monthNumber = monthNumbers.get(month);
                    bloomMonthNumbers.add(monthNumber);

                }
                return bloomMonthNumbers;

            }

        }

        return new ArrayList<>();

    }

}
