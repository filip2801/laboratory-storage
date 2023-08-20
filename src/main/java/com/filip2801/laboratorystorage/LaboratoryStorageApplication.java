package com.filip2801.laboratorystorage;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan
@EnableJpaRepositories
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Laboratory storage API", version = "2.0", description = "Laboratory storage"))
public class LaboratoryStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaboratoryStorageApplication.class, args);
    }

}
