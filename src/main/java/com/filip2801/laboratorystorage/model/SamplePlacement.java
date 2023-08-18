package com.filip2801.laboratorystorage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class SamplePlacement {

    @Id
    private UUID sampleId;
    private UUID locationId;
    private UUID employeeId;
    private LocalDateTime updatedAt;

    public void changeLocation(UUID locationId, UUID employeeId) {
        this.locationId = locationId;
        this.employeeId = employeeId;
        this.updatedAt = LocalDateTime.now();
    }
}
