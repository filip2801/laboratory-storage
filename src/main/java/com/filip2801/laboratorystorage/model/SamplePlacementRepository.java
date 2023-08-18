package com.filip2801.laboratorystorage.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SamplePlacementRepository extends JpaRepository<SamplePlacement, UUID> {

    Optional<SamplePlacement> findBySampleId(UUID sampleId);
}
