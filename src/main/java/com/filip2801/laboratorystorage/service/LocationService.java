package com.filip2801.laboratorystorage.service;

import com.filip2801.laboratorystorage.dto.LocationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LocationService {

    public List<LocationDto> getRootLocations() {
        return List.of();
    }

    public List<LocationDto> getChildrenLocations(UUID locationId) {
        return List.of();
    }
}
