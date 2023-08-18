package com.filip2801.laboratorystorage.dto;

import com.filip2801.laboratorystorage.model.LocationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SampleLocationPathDto {
    private UUID locationId;
    private String name;
    private LocationType type;
    private SampleLocationPathDto child;
}
