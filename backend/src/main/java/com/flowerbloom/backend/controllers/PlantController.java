package com.flowerbloom.backend.controllers;

import com.flowerbloom.backend.services.TrefleService;
import com.flowerbloom.backend.services.INaturalistService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private final TrefleService trefleService;
    private final INaturalistService iNaturalistService;

    public PlantController(
            TrefleService trefleService,
            INaturalistService iNaturalistService
    ) {
        this.trefleService = trefleService;
        this.iNaturalistService = iNaturalistService;
    }

    @GetMapping
    public String getPlants(){
        return trefleService.getPlants();
    }

    @GetMapping("/taxon")
    public Integer getTaxonId(@RequestParam String scientificName) {
        return iNaturalistService.getTaxonId(scientificName);
    }

    @GetMapping("/histogram")
    public Map<Integer, Integer> getFloweringHistogram(@RequestParam Integer taxonId) {
        return iNaturalistService.getFloweringHistogram(taxonId);
    }
}
