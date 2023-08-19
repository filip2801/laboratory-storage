package com.filip2801.laboratorystorage.service

import com.filip2801.laboratorystorage.model.Location
import com.filip2801.laboratorystorage.model.LocationInMemoryRepository
import com.filip2801.laboratorystorage.model.LocationRepository
import com.filip2801.laboratorystorage.model.LocationType
import com.filip2801.laboratorystorage.model.SamplePlacement
import com.filip2801.laboratorystorage.model.SamplePlacementHistoryInMemoryRepository
import com.filip2801.laboratorystorage.model.SamplePlacementInMemoryRepository
import com.filip2801.laboratorystorage.model.SamplePlacementRepository
import spock.lang.Specification

import static java.util.UUID.randomUUID

class SamplePlacementServiceSpec extends Specification {

    SamplePlacementService samplePlacementService
    SamplePlacementRepository samplePlacementRepository
    LocationRepository locationRepository

    def setup() {
        samplePlacementRepository = new SamplePlacementInMemoryRepository()
        locationRepository = new LocationInMemoryRepository()
        samplePlacementService = new SamplePlacementService(samplePlacementRepository, new SamplePlacementHistoryInMemoryRepository(), locationRepository)
    }

    def "should find sample placement details"() {
        given:
        def sampleId = randomUUID()
        def employeeId = randomUUID()

        def rootLocationId = randomUUID()
        def parentLocationId = randomUUID()
        def sampleLocationId = randomUUID()

        samplePlacementRepository.save(SamplePlacement.builder()
                .sampleId(sampleId)
                .locationId(sampleLocationId)
                .employeeId(employeeId)
                .build())

        locationRepository.saveAll([
                Location.builder().locationId(sampleLocationId).name("1.2").type(LocationType.ROOM)
                        .parentId(parentLocationId).path([rootLocationId, parentLocationId]).build(),
                Location.builder().locationId(parentLocationId).name("C13").type(LocationType.BUILDING)
                        .parentId(rootLocationId).path([rootLocationId]).build(),
                Location.builder().locationId(rootLocationId).name("Wroclaw").type(LocationType.CITY)
                        .parentId(null).path([]).build()])

        when:
        var foundSamplePlacementDetails = samplePlacementService.findSamplePlacementDetails(sampleId)

        then:
        foundSamplePlacementDetails.isPresent()
        def details = foundSamplePlacementDetails.get()
        details.sampleId == sampleId
        details.locationId == sampleLocationId
        details.employeeId == employeeId

        details.locationPath.name == "Wroclaw"
        details.locationPath.locationId == rootLocationId
        details.locationPath.type == LocationType.CITY

        details.locationPath.child.name == "C13"
        details.locationPath.child.locationId == parentLocationId
        details.locationPath.child.type == LocationType.BUILDING

        details.locationPath.child.child.name == "1.2"
        details.locationPath.child.child.locationId == sampleLocationId
        details.locationPath.child.child.type == LocationType.ROOM
    }

    def "should not find not existing sample placement"() {
        when:
        var foundSamplePlacementDetails = samplePlacementService.findSamplePlacementDetails(randomUUID())

        then:
        foundSamplePlacementDetails.isEmpty()
    }

}
