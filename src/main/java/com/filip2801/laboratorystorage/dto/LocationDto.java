package com.filip2801.laboratorystorage.dto;

import com.filip2801.laboratorystorage.model.LocationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    private String name;
    @NotNull
    private LocationType type;
    private UUID parentId;
}
