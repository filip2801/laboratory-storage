package com.filip2801.laboratorystorage.dto;

import jakarta.validation.constraints.NotNull;
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
public class SamplePlacementDto {
    private UUID sampleId;
    @NotNull
    private UUID locationId;
    @NotNull
    private UUID employeeId;
    private LocalDateTime updatedAt;
}
