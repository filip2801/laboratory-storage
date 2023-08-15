package com.filip2801.laboratorystorage

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class LaboratoryStorageApplicationTest extends Specification {

    @Autowired
    ApplicationContext webApplicationContext

    def "should start context"() {
        expect:
        webApplicationContext
    }
}
