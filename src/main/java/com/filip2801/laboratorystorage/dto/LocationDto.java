package com.filip2801.laboratorystorage.dto;

import com.filip2801.laboratorystorage.model.LocationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private UUID locationId;
    private String name;
    private LocationType type;
    private UUID parentId;
}
