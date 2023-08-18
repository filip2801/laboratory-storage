package com.filip2801.laboratorystorage.web;

import com.filip2801.laboratorystorage.dto.SamplePlacementDetailsDto;
import com.filip2801.laboratorystorage.dto.SamplePlacementDto;
import com.filip2801.laboratorystorage.model.SamplePlacement;
import com.filip2801.laboratorystorage.service.SamplePlacementService;
import com.filip2801.laboratorystorage.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/samples/{sampleId}/placement")
@RestController
public class SamplePlacementController {

    private final SamplePlacementService samplePlacementService;

    @PutMapping
    SamplePlacementDto changeSampleLocation(@PathVariable UUID sampleId, @RequestBody SamplePlacementDto samplePlacementDto) {
        var updatedSample = samplePlacementService.changeSampleLocation(sampleId, samplePlacementDto);
        return toDto(updatedSample);
    }

    @GetMapping
    SamplePlacementDto getSampleLocation(@PathVariable UUID sampleId) {
        return samplePlacementService.findSampleLocation(sampleId).map(this::toDto).orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping("/details")
    SamplePlacementDetailsDto getSampleLocationDetails(@PathVariable UUID sampleId) {
        return samplePlacementService.findSamplePlacementDetails(sampleId).orElseThrow(ResourceNotFoundException::new);
    }

    private SamplePlacementDto toDto(SamplePlacement samplePlacement) {
        return SamplePlacementDto.builder()
                .sampleId(samplePlacement.getSampleId())
                .locationId(samplePlacement.getLocationId())
                .employeeId(samplePlacement.getEmployeeId())
                .updatedAt(samplePlacement.getUpdatedAt())
                .build();
    }

}
