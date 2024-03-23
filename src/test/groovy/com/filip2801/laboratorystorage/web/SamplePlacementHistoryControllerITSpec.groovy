package com.filip2801.laboratorystorage.web

import com.filip2801.laboratorystorage.IntegrationTestSpecification
import com.filip2801.laboratorystorage.dto.LocationDto
import com.filip2801.laboratorystorage.model.LocationType
import com.filip2801.laboratorystorage.model.SamplePlacement
import com.filip2801.laboratorystorage.model.SamplePlacementRepository
import com.filip2801.laboratorystorage.service.LocationService
import com.filip2801.laboratorystorage.service.SamplePlacementService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

import static com.filip2801.laboratorystorage.TestUtils.isOneSecondCloseToNow
import static com.filip2801.laboratorystorage.TestUtils.parseLocalDateTime
import static java.util.UUID.randomUUID

class SamplePlacementHistoryControllerITSpec extends IntegrationTestSpecification {

    @Autowired
    LocationService locationService

    def "should get sample placement history"() {
        given:
        var firstLocationId = createSomeLocation()
        def firstLocationRequest = [
                locationId: firstLocationId,
                employeeId: randomUUID()
        ]

        def sampleId = randomUUID()
        sendPut("samples/$sampleId/placement", firstLocationRequest)

        def secondLocationId = createSomeLocation()
        def employeeId = randomUUID()
        def secondLocationRequest = [
                locationId: secondLocationId,
                employeeId: employeeId
        ]
        sendPut("samples/$sampleId/placement", secondLocationRequest)

        when:
        var historyResponse = sendGetForList("samples/$sampleId/placement-history")

        then:
        historyResponse.statusCode == HttpStatus.OK
        historyResponse.body[0].sampleId == sampleId.toString()
        historyResponse.body[0].locationId == firstLocationRequest.locationId.toString()
        historyResponse.body[0].employeeId == firstLocationRequest.employeeId.toString()

        historyResponse.body[1].sampleId == sampleId.toString()
        historyResponse.body[1].locationId == secondLocationRequest.locationId.toString()
        historyResponse.body[1].employeeId == secondLocationRequest.employeeId.toString()
    }

    private UUID createSomeLocation() {
        var location = locationService.createLocation(LocationDto.builder()
                .name(randomUUID().toString())
                .type(LocationType.CITY)
                .parentId(null)
                .build());
        return location.locationId
    }
}
