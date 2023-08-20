package com.filip2801.laboratorystorage.web;

import com.filip2801.laboratorystorage.dto.LocationDto;
import com.filip2801.laboratorystorage.model.Location;
import com.filip2801.laboratorystorage.service.LocationService;
import com.filip2801.laboratorystorage.web.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/locations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    @Operation(summary = "Create new location", description = "Location id is created by server")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Parent location does not exist")})
    LocationDto createLocation(@Valid @RequestBody LocationDto location) {
        var createdLocation = locationService.createLocation(location);
        return toDto(createdLocation);
    }

    @GetMapping("/{locationId}")
    @Operation(summary = "Get location")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Location does not exist")})
    LocationDto findLocation(@PathVariable UUID locationId) {
        return locationService.findByLocationId(locationId).map(this::toDto).orElseThrow(ResourceNotFoundException::new);
    }

    private LocationDto toDto(Location location) {
        return LocationDto.builder()
                .locationId(location.getLocationId())
                .name(location.getName())
                .type(location.getType())
                .parentId(location.getParentId())
                .build();
    }

}
