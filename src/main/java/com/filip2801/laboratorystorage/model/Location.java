package com.filip2801.laboratorystorage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Location {

    @Id
    private UUID locationId;

    private String name;

    @Enumerated(EnumType.STRING)
    private LocationType type;
    private UUID parentId;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<UUID> path = new ArrayList<>();

}
