package com.filip2801.laboratorystorage.web;

import com.filip2801.laboratorystorage.dto.LocationDto;
import com.filip2801.laboratorystorage.model.Location;
import com.filip2801.laboratorystorage.service.LocationService;
import com.filip2801.laboratorystorage.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/locations")
@RestController
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    LocationDto createLocation(@RequestBody LocationDto location) {
        var createdLocation = locationService.createLocation(location);
        return toDto(createdLocation);
    }

    @GetMapping("/{locationId}")
    LocationDto findLocation(@PathVariable UUID locationId) {
        return locationService.findByLocationId(locationId).map(this::toDto).orElseThrow(ResourceNotFoundException::new);
    }

    private LocationDto toDto(Location location) {
        return LocationDto.builder()
                .locationId(location.getLocationId())
                .name(location.getName())
                .type(location.getType())
                .parentId(location.getParentId())
                .path(location.getPath())
                .build();
    }

}
