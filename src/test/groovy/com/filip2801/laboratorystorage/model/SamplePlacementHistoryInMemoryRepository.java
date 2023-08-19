package com.filip2801.laboratorystorage.model;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SamplePlacementHistoryInMemoryRepository extends AbstractInMemoryCrudRepository<SamplePlacementHistory, SamplePlacementHistoryPK> implements SamplePlacementHistoryRepository {

    @Override
    public List<SamplePlacementHistory> findAllBySampleIdOrderByUpdatedAtAsc(UUID sampleId) {
        return storage.values().stream().filter(it -> it.getSampleId().equals(sampleId))
                .sorted(Comparator.comparing(SamplePlacementHistory::getUpdatedAt))
                .collect(Collectors.toList());
    }
}
