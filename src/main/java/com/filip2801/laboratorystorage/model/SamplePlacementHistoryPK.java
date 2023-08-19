package com.filip2801.laboratorystorage.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class SamplePlacementHistoryPK implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID sampleId;
    private LocalDateTime updatedAt;
}
