package com.filip2801.laboratorystorage.web;

import com.filip2801.laboratorystorage.model.Location;
import com.filip2801.laboratorystorage.model.LocationRepository;
import com.filip2801.laboratorystorage.model.LocationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/locations")
@RestController
public class LocationsController {

    private final LocationRepository locationRepository;

    @PostMapping
    void create() {
        String name = UUID.randomUUID().toString();
        log.info("Creating location '{}'", name);

        var location = Location.builder()
                .locationId(UUID.randomUUID())
                .parentId(null)
                .type(LocationType.BUILDING)
                .name(name)
                .build();

        locationRepository.save(location);
    }

}
