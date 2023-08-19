package com.filip2801.laboratorystorage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@IdClass(SamplePlacementHistoryPK.class)
public class SamplePlacementHistory {

    @Id
    private UUID sampleId;

    @Id
    private LocalDateTime updatedAt;

    private UUID locationId;
    private UUID employeeId;

}
