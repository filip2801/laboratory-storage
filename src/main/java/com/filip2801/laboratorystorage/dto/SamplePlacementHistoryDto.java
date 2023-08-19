package com.filip2801.laboratorystorage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SamplePlacementHistoryDto {
    private UUID sampleId;
    private UUID locationId;
    private UUID employeeId;
    private LocalDateTime updatedAt;
}
