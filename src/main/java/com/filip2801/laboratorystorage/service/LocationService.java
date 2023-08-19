package com.filip2801.laboratorystorage.service;

import com.filip2801.laboratorystorage.dto.LocationDto;
import com.filip2801.laboratorystorage.model.Location;
import com.filip2801.laboratorystorage.model.LocationRepository;
import com.filip2801.laboratorystorage.web.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public Location createLocation(LocationDto dto) {
        List<UUID> path;
        if (dto.getParentId() != null) {
            var parentLocation = locationRepository.findById(dto.getParentId()).orElseThrow(BadRequestException::new);
            path = new ArrayList<>(parentLocation.getPath());
            path.add(parentLocation.getLocationId());
        } else {
            path = List.of();
        }

        var location = Location.builder()
                .locationId(UUID.randomUUID())
                .name(dto.getName())
                .type(dto.getType())
                .parentId(dto.getParentId())
                .path(path)
                .build();

        return locationRepository.save(location);
    }

    public Optional<Location> findByLocationId(UUID locationId) {
        return locationRepository.findById(locationId);
    }
}
