package com.filip2801.laboratorystorage.model;

import java.util.UUID;

public class LocationInMemoryRepository extends AbstractInMemoryCrudRepository<Location, UUID> implements LocationRepository {

    @Override
    public Location save(Location entity) {
        return storage.put(entity.getLocationId(), entity);
    }

}
