package com.filip2801.laboratorystorage.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class SamplePlacementHistoryPK implements Serializable {
    private UUID sampleId;
    private LocalDateTime updatedAt;
}
