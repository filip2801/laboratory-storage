package com.filip2801.laboratorystorage

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

@ContextConfiguration(initializers = DbInitializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestTemplateTestConfig)
class IntegrationTestSpecification extends Specification {

    @Autowired
    RestTemplate restTemplate

    @LocalServerPort
    int port
    @Value('${server.servlet.context-path:/}')
    String contextPath

    String getBaseUrl() {
        return "http://localhost:${port}${contextPath}"
    }

    def sendGetForObject(String url) {
        restTemplate.getForEntity("${getBaseUrl()}/${url}", HashMap)
    }

    def sendPost(String url, Object requestPayload) {
        restTemplate.postForEntity("${getBaseUrl()}/${url}", requestPayload, HashMap)
    }

    def sendPut(String url, Object requestPayload) {
        restTemplate.exchange("${getBaseUrl()}/${url}", HttpMethod.PUT, new HttpEntity<Object>(requestPayload), HashMap)
    }
}
