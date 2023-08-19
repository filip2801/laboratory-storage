package com.filip2801.laboratorystorage

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

class LaboratoryStorageApplicationSpec extends IntegrationTestSpecification {

    @Autowired
    ApplicationContext webApplicationContext

    def "should start context"() {
        expect:
        webApplicationContext
    }
}
