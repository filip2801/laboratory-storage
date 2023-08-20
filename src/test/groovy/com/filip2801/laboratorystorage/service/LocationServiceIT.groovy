package com.filip2801.laboratorystorage.service

import com.filip2801.laboratorystorage.IntegrationTestSpecification
import com.filip2801.laboratorystorage.dto.LocationDto
import com.filip2801.laboratorystorage.model.LocationRepository
import com.filip2801.laboratorystorage.model.LocationType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import spock.lang.Specification

import static com.filip2801.laboratorystorage.model.LocationType.BUILDING
import static com.filip2801.laboratorystorage.model.LocationType.CITY
import static com.filip2801.laboratorystorage.model.LocationType.ROOM

class LocationServiceIT extends IntegrationTestSpecification {

    @Autowired
    LocationService locationService


    def "should create root location"() {
        given:
        def locationToCreate = LocationDto.builder()
                .name('London')
                .type(CITY)
                .build()

        when:
        var cratedLocation = locationService.createLocation(locationToCreate)

        then:
        cratedLocation.name == 'London'
        cratedLocation.type == CITY
        cratedLocation.path == []
        cratedLocation.locationId
        !cratedLocation.parentId
    }

    def "should create child location"() {
        given:
        def rootLocationToCreate = LocationDto.builder()
                .name('London')
                .type(CITY)
                .build()
        var rootLocation = locationService.createLocation(rootLocationToCreate)

        def childLocationToCreate = LocationDto.builder()
                .name('D1')
                .type(BUILDING)
                .parentId(rootLocation.locationId)
                .build()

        when:
        var childLocation = locationService.createLocation(childLocationToCreate)

        then:
        childLocation.name == 'D1'
        childLocation.type == BUILDING
        childLocation.locationId
        childLocation.parentId == rootLocation.locationId
        childLocation.path == [rootLocation.locationId]
    }

    def "should create second child location"() {
        given:
        def rootLocationToCreate = LocationDto.builder()
                .name('London')
                .type(CITY)
                .build()
        var rootLocation = locationService.createLocation(rootLocationToCreate)

        def parentLocationToCreate = LocationDto.builder()
                .name('D1')
                .type(BUILDING)
                .parentId(rootLocation.locationId)
                .build()
        var parentLocation = locationService.createLocation(parentLocationToCreate)

        def childLocationToCreate = LocationDto.builder()
                .name('1.2')
                .type(ROOM)
                .parentId(parentLocation.locationId)
                .build()

        when:
        var childLocation = locationService.createLocation(childLocationToCreate)

        then:
        childLocation.name == '1.2'
        childLocation.type == ROOM
        childLocation.locationId
        childLocation.parentId == parentLocation.locationId
        childLocation.path == [rootLocation.locationId, parentLocation.locationId]
    }

}
