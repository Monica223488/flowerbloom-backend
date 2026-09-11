package com.flowerbloom.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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


}
