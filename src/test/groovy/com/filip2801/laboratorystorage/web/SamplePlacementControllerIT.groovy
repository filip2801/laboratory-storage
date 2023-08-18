package com.filip2801.laboratorystorage.web

import com.filip2801.laboratorystorage.IntegrationTestSpecification
import com.filip2801.laboratorystorage.model.Location
import com.filip2801.laboratorystorage.model.LocationRepository
import com.filip2801.laboratorystorage.model.LocationType
import com.filip2801.laboratorystorage.model.SamplePlacement
import com.filip2801.laboratorystorage.model.SamplePlacementRepository
import com.filip2801.laboratorystorage.service.SamplePlacementService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

import static java.util.UUID.randomUUID

class SamplePlacementControllerIT extends IntegrationTestSpecification {

    @Autowired
    SamplePlacementService samplePlacementService
    @Autowired
    SamplePlacementRepository samplePlacementRepository
    @Autowired
    LocationRepository locationRepository

    def "should create sample placement"() {
        given:
        def locationId = randomUUID()
        def employeeId = randomUUID()
        def requestPayload = [
                locationId: locationId,
                employeeId: employeeId
        ]

        def sampleId = randomUUID()

        when:
        var putResponse = sendPut("samples/$sampleId/placement", requestPayload)

        then:
        putResponse.statusCode == HttpStatus.OK
        putResponse.body.sampleId == sampleId.toString()
        putResponse.body.locationId == locationId.toString()
        putResponse.body.employeeId == employeeId.toString()

        and:
        def getResponse= sendGetForObject("/samples/$sampleId/placement")
        getResponse.statusCode == HttpStatus.OK
        getResponse.body.sampleId == sampleId.toString()
        getResponse.body.locationId == locationId.toString()
        getResponse.body.employeeId == employeeId.toString()
        getResponse.body.updatedAt // todo check date format
    }

    def "should change sample placement"() {
        given:
        def createRequestPayload = [
                locationId: randomUUID(),
                employeeId: randomUUID()
        ]

        def sampleId = randomUUID()
        var creationResponse = sendPut("samples/$sampleId/placement", createRequestPayload)

        def locationId = randomUUID()
        def employeeId = randomUUID()
        def updateRequestPayload = [
                locationId: locationId,
                employeeId: employeeId
        ]

        when:
        var updateResponse = sendPut("samples/$sampleId/placement", updateRequestPayload)

        then:
        updateResponse.statusCode == HttpStatus.OK
        updateResponse.body.sampleId == sampleId.toString()
        updateResponse.body.locationId == locationId.toString()
        updateResponse.body.employeeId == employeeId.toString()

        and:
        def getResponse= sendGetForObject("/samples/$sampleId/placement")
        getResponse.statusCode == HttpStatus.OK
        getResponse.body.sampleId == sampleId.toString()
        getResponse.body.locationId == locationId.toString()
        getResponse.body.employeeId == employeeId.toString()
        // todo compare dates
        // getResponse.body.updatedAt.isAfter(creationResponse.body.updatedAt)
    }


    def "should get sample placement details"() {
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
        var detailsResponse = sendGetForObject("/samples/$sampleId/placement/details")

        then:
        detailsResponse.statusCode == HttpStatus.OK
        def details = detailsResponse.body
        details.sampleId == sampleId.toString()
        details.locationId == sampleLocationId.toString()
        details.employeeId == employeeId.toString()

        details.locationPath.name == "Wroclaw"
        details.locationPath.locationId == rootLocationId.toString()
        details.locationPath.type == LocationType.CITY.name()

        details.locationPath.child.name == "C13"
        details.locationPath.child.locationId == parentLocationId.toString()
        details.locationPath.child.type == LocationType.BUILDING.name()

        details.locationPath.child.child.name == "1.2"
        details.locationPath.child.child.locationId == sampleLocationId.toString()
        details.locationPath.child.child.type == LocationType.ROOM.name()
    }

    def "should return 404 when the is no sample placement"() {
        when:
        sendGetForObject("/samples/${randomUUID()}/placement")

        then:
        thrown status404()
    }

    def "should return 404 when the is no sample placement details"() {
        when:
        sendGetForObject("/samples/${randomUUID()}/placement/details")

        then:
        thrown status404()
    }

    private Class status404() {
        org.springframework.web.client.HttpClientErrorException$NotFound
    }

}
