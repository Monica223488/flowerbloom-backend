package com.flowerbloom.backend.services;

import com.flowerbloom.backend.dtos.FloweringResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PlantService {

    private final TrefleService trefleService;
    private final INaturalistService iNaturalistService;

    public PlantService(
            TrefleService trefleService,
            INaturalistService iNaturalistService
    ) {
        this.trefleService = trefleService;
        this.iNaturalistService = iNaturalistService;
    }

    public FloweringResponseDto getFloweringData(String scientificName) {

        List<Integer> expectedBloomMonths =
                trefleService.getBloomMonths(scientificName);

        Integer taxonId =
                iNaturalistService.getTaxonId(scientificName);

        Map<Integer, Integer> floweringObservations =
                iNaturalistService.getFloweringHistogram(taxonId);

        return new FloweringResponseDto(
                expectedBloomMonths,
                floweringObservations
        );
    }
}
