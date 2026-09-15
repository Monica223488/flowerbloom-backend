package com.flowerbloom.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FloweringResponseDto {
    private List<Integer> expectedBloomMonths;
    private Map<Integer, Integer> floweringObservations;
}
