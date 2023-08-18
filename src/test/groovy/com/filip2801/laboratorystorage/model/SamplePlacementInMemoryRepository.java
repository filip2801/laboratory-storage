package com.filip2801.laboratorystorage.model;

import java.util.Optional;
import java.util.UUID;

public class SamplePlacementInMemoryRepository extends AbstractInMemoryCrudRepository<SamplePlacement, UUID> implements SamplePlacementRepository {

    @Override
    public SamplePlacement save(SamplePlacement entity) {
        return storage.put(entity.getSampleId(), entity);
    }

    @Override
    public Optional<SamplePlacement> findBySampleId(UUID sampleId) {
        return findById(sampleId);
    }
}
