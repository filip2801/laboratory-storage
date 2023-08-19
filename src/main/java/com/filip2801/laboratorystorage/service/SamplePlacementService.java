package com.filip2801.laboratorystorage.service;

import com.filip2801.laboratorystorage.dto.SampleLocationPathDto;
import com.filip2801.laboratorystorage.dto.SamplePlacementDetailsDto;
import com.filip2801.laboratorystorage.dto.SamplePlacementDto;
import com.filip2801.laboratorystorage.model.Location;
import com.filip2801.laboratorystorage.model.LocationRepository;
import com.filip2801.laboratorystorage.model.SamplePlacement;
import com.filip2801.laboratorystorage.model.SamplePlacementRepository;
import com.filip2801.laboratorystorage.web.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SamplePlacementService {

    private final SamplePlacementRepository samplePlacementRepository;
    private final LocationRepository locationRepository;

    public SamplePlacement changeSampleLocation(UUID sampleId, SamplePlacementDto samplePlacementDto) {
        validate(samplePlacementDto);

        var foundSamplePlacement = samplePlacementRepository.findBySampleId(sampleId);
        if (foundSamplePlacement.isPresent()) {
            var samplePlacement = foundSamplePlacement.get();
            samplePlacement.changeLocation(samplePlacementDto.getLocationId(), samplePlacementDto.getEmployeeId());
            return samplePlacementRepository.save(samplePlacement);
        } else {
            var newPlacement = SamplePlacement.builder()
                    .sampleId(sampleId)
                    .locationId(samplePlacementDto.getLocationId())
                    .employeeId(samplePlacementDto.getEmployeeId())
                    .updatedAt(LocalDateTime.now())
                    .build();
            return samplePlacementRepository.save(newPlacement);
        }
    }

    private void validate(SamplePlacementDto samplePlacementDto) {
        if (!locationRepository.existsById(samplePlacementDto.getLocationId())) {
            throw new BadRequestException("Location " + samplePlacementDto.getLocationId() + " does not exist");
        }
    }

    public Optional<SamplePlacement> findSampleLocation(UUID sampleId) {
        return samplePlacementRepository.findBySampleId(sampleId);
    }

    public Optional<SamplePlacementDetailsDto> findSamplePlacementDetails(UUID sampleId) {
        var foundSamplePlacement = samplePlacementRepository.findBySampleId(sampleId);
        if (foundSamplePlacement.isEmpty()) {
            return Optional.empty();
        }
        var locationId = foundSamplePlacement.get().getLocationId();
        Location location = locationRepository.findById(locationId).get();

        Map<UUID, Location> locationsInPath = getLocationsInPath(location);

        SampleLocationPathDto locationPath = toSampleLocationPathDto(location, locationsInPath);
        return Optional.of(SamplePlacementDetailsDto.builder()
                .sampleId(foundSamplePlacement.get().getSampleId())
                .locationId(locationId)
                .employeeId(foundSamplePlacement.get().getEmployeeId())
                .updatedAt(foundSamplePlacement.get().getUpdatedAt())
                .locationPath(locationPath)
                .build());
    }

    private Map<UUID, Location> getLocationsInPath(Location location) {
        return locationRepository.findAllById(location.getPath()).stream()
                .collect(Collectors.toMap(Location::getLocationId, Function.identity()));
    }

    private SampleLocationPathDto toSampleLocationPathDto(Location location, Map<UUID, Location> locationsInPath) {
        return toSampleLocationPathDto(
                location,
                locationsInPath,
                null);
    }

    private SampleLocationPathDto toSampleLocationPathDto(Location location, Map<UUID, Location> locationsInPath, SampleLocationPathDto child) {
        var locationPath = SampleLocationPathDto.builder()
                .locationId(location.getLocationId())
                .name(location.getName())
                .type(location.getType())
                .child(child)
                .build();

        if (location.getParentId() != null) {
            return toSampleLocationPathDto(
                    locationsInPath.get(location.getParentId()),
                    locationsInPath,
                    locationPath);
        } else {
            return locationPath;
        }
    }
}
