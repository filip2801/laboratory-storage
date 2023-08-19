package com.filip2801.laboratorystorage.web

import com.filip2801.laboratorystorage.IntegrationTestSpecification
import com.filip2801.laboratorystorage.dto.LocationDto
import com.filip2801.laboratorystorage.model.Location
import com.filip2801.laboratorystorage.model.LocationRepository
import com.filip2801.laboratorystorage.model.LocationType
import com.filip2801.laboratorystorage.model.SamplePlacement
import com.filip2801.laboratorystorage.model.SamplePlacementRepository
import com.filip2801.laboratorystorage.service.LocationService
import com.filip2801.laboratorystorage.service.SamplePlacementService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

import java.time.format.DateTimeFormatter

import static com.filip2801.laboratorystorage.TestUtils.isOneSecondCloseToNow
import static com.filip2801.laboratorystorage.TestUtils.parseLocalDateTime
import static java.util.UUID.randomUUID

class SamplePlacementControllerIT extends IntegrationTestSpecification {

    @Autowired
    SamplePlacementService samplePlacementService
    @Autowired
    SamplePlacementRepository samplePlacementRepository
    @Autowired
    LocationService locationService

    def "should create sample placement"() {
        given:
        def locationId = createSomeLocation()
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
        isOneSecondCloseToNow(parseLocalDateTime(getResponse.body.updatedAt))
    }

    def "should change sample placement"() {
        given:
        var firstLocationId = createSomeLocation()
        def createRequestPayload = [
                locationId: firstLocationId,
                employeeId: randomUUID()
        ]

        def sampleId = randomUUID()
        var creationResponse = sendPut("samples/$sampleId/placement", createRequestPayload)

        def locationId = createSomeLocation()
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
        parseLocalDateTime(getResponse.body.updatedAt).isAfter(parseLocalDateTime(creationResponse.body.updatedAt))
    }

    def "should get sample placement details"() {
        given:
        def sampleId = randomUUID()
        def employeeId = randomUUID()

        var rootLocation = locationService.createLocation(LocationDto.builder().name("Wroclaw").type(LocationType.CITY).parentId(null).build());
        var parentLocation = locationService.createLocation(LocationDto.builder().name("C13").type(LocationType.BUILDING).parentId(rootLocation.locationId).build())
        var sampleLocation = locationService.createLocation(LocationDto.builder().name("1.2").type(LocationType.ROOM).parentId(parentLocation.locationId).build())

        samplePlacementRepository.save(SamplePlacement.builder()
                .sampleId(sampleId)
                .locationId(sampleLocation.locationId)
                .employeeId(employeeId)
                .build())

        when:
        var detailsResponse = sendGetForObject("/samples/$sampleId/placement/details")

        then:
        detailsResponse.statusCode == HttpStatus.OK
        def details = detailsResponse.body
        details.sampleId == sampleId.toString()
        details.locationId == sampleLocation.locationId.toString()
        details.employeeId == employeeId.toString()

        details.locationPath.name == "Wroclaw"
        details.locationPath.locationId == rootLocation.locationId.toString()
        details.locationPath.type == LocationType.CITY.name()

        details.locationPath.child.name == "C13"
        details.locationPath.child.locationId == parentLocation.locationId.toString()
        details.locationPath.child.type == LocationType.BUILDING.name()

        details.locationPath.child.child.name == "1.2"
        details.locationPath.child.child.locationId == sampleLocation.locationId.toString()
        details.locationPath.child.child.type == LocationType.ROOM.name()
    }

    def "should return 404 when there is no sample placement"() {
        when:
        sendGetForObject("/samples/${randomUUID()}/placement")

        then:
        thrown status404()
    }

    def "should return 404 when there is no sample placement details"() {
        when:
        sendGetForObject("/samples/${randomUUID()}/placement/details")

        then:
        thrown status404()
    }

    def "should not create sample placement and return 400 when location does not exist"() {
        given:
        def locationId = randomUUID()
        def employeeId = randomUUID()
        def requestPayload = [
                locationId: locationId,
                employeeId: employeeId
        ]

        def sampleId = randomUUID()

        when:
        sendPut("samples/$sampleId/placement", requestPayload)

        then:
        thrown status400()
    }

    private UUID createSomeLocation() {
        var location = locationService.createLocation(LocationDto.builder()
                .name(randomUUID().toString())
                .type(LocationType.CITY)
                .parentId(null)
                .build());
        return location.locationId
    }

    private Class status400() {
        org.springframework.web.client.HttpClientErrorException$BadRequest
    }

    private Class status404() {
        org.springframework.web.client.HttpClientErrorException$NotFound
    }
}
