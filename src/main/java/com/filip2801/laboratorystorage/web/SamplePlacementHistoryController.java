package com.filip2801.laboratorystorage.web;

import com.filip2801.laboratorystorage.dto.SamplePlacementHistoryDto;
import com.filip2801.laboratorystorage.model.SamplePlacementHistory;
import com.filip2801.laboratorystorage.model.SamplePlacementHistoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/samples/{sampleId}/placement-history", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class SamplePlacementHistoryController {

    private final SamplePlacementHistoryRepository samplePlacementHistoryRepository;

    @GetMapping
    @Operation(summary = "Get sample placement history")
    @ApiResponse(responseCode = "200")
    List<SamplePlacementHistoryDto> findSampleLocationHistory(@PathVariable UUID sampleId) {
        return samplePlacementHistoryRepository.findAllBySampleIdOrderByUpdatedAtAsc(sampleId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private SamplePlacementHistoryDto toDto(SamplePlacementHistory samplePlacement) {
        return SamplePlacementHistoryDto.builder()
                .sampleId(samplePlacement.getSampleId())
                .locationId(samplePlacement.getLocationId())
                .employeeId(samplePlacement.getEmployeeId())
                .updatedAt(samplePlacement.getUpdatedAt())
                .build();
    }

}
