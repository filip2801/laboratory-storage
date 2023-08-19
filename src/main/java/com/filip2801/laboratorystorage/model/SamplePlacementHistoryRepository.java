package com.filip2801.laboratorystorage.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SamplePlacementHistoryRepository extends JpaRepository<SamplePlacementHistory, SamplePlacementHistoryPK> {

    List<SamplePlacementHistory> findAllBySampleIdOrderByUpdatedAtAsc(UUID sampleId);
}
