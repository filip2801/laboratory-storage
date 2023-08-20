package com.filip2801.laboratorystorage.web;

import com.filip2801.laboratorystorage.dto.SamplePlacementDetailsDto;
import com.filip2801.laboratorystorage.dto.SamplePlacementDto;
import com.filip2801.laboratorystorage.model.SamplePlacement;
import com.filip2801.laboratorystorage.service.SamplePlacementService;
import com.filip2801.laboratorystorage.web.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/samples/{sampleId}/placement", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class SamplePlacementController {

    private final SamplePlacementService samplePlacementService;

    @PutMapping
    @Operation(summary = "Change sample location")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Location does not exist")})
    SamplePlacementDto changeSampleLocation(@Valid @PathVariable UUID sampleId, @RequestBody SamplePlacementDto samplePlacementDto) {
        var updatedSample = samplePlacementService.changeSampleLocation(sampleId, samplePlacementDto);
        return toDto(updatedSample);
    }

    @GetMapping
    @Operation(summary = "Get sample location")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Sample does not exist")})
    SamplePlacementDto getSampleLocation(@PathVariable UUID sampleId) {
        return samplePlacementService.findSampleLocation(sampleId).map(this::toDto).orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping("/details")
    @Operation(summary = "Get sample location", description = "Returns sample placement with path to location")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Sample does not exist")})
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
