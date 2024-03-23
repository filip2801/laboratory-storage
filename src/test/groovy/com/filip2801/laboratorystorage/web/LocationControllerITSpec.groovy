package com.filip2801.laboratorystorage.web

import com.filip2801.laboratorystorage.IntegrationTestSpecification
import com.filip2801.laboratorystorage.model.*
import com.filip2801.laboratorystorage.service.SamplePlacementService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

import static java.util.UUID.randomUUID

class LocationControllerITSpec extends IntegrationTestSpecification {

    def "should create root location"() {
        given:
        def requestPayload = [
                name: 'London',
                type: 'CITY'
        ]

        when:
        var creationResponse = sendPost("locations", requestPayload)

        then:
        creationResponse.statusCode == HttpStatus.OK
        creationResponse.body.name == 'London'
        creationResponse.body.type == 'CITY'
        creationResponse.body.locationId
        !creationResponse.body.parentId

        and:
        def getResponse = sendGetForObject("/locations/${creationResponse.body.locationId}")
        getResponse.statusCode == HttpStatus.OK
        getResponse.body.name == 'London'
        getResponse.body.type == 'CITY'
        getResponse.body.locationId == creationResponse.body.locationId
    }

    def "should create child location"() {
        given:
        def rootLocationCreateRequest = [
                name: 'London',
                type: 'CITY'
        ]
        var rootLocationResponse = sendPost("locations", rootLocationCreateRequest)


        def childLocationCreateRequest = [
                name    : 'D1',
                type    : 'BUILDING',
                parentId: rootLocationResponse.body.locationId
        ]

        when:
        var childLocationResponse = sendPost("locations", childLocationCreateRequest)

        then:
        childLocationResponse.statusCode == HttpStatus.OK
        childLocationResponse.body.name == 'D1'
        childLocationResponse.body.type == 'BUILDING'
        childLocationResponse.body.locationId
        childLocationResponse.body.parentId == rootLocationResponse.body.locationId
    }

    def "should not create location and return 400 when parent id does not exist"() {
        given:
        def requestPayload = [
                name    : 'London',
                type    : 'CITY',
                parentId: randomUUID().toString()
        ]

        when:
        sendPost("locations", requestPayload)

        then:
        thrown status400()
    }

    def "should return 404 when there is no location with given id"() {
        when:
        sendGetForObject("/locations/${randomUUID()}")

        then:
        thrown status404()
    }

    private Class status400() {
        org.springframework.web.client.HttpClientErrorException$BadRequest
    }

    private Class status404() {
        org.springframework.web.client.HttpClientErrorException$NotFound
    }

}
