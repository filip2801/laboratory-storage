# Laboratory storage
Application for tracking samples placement in laboratory. Implemented as Marcel interview task

## Task description
Where is my test tube?
Imagine a gigantic laboratory network with many buildings in many locations, numerous rooms, lab workers, equipment, storage compartments, etc.
Your job is to create a service to track the placement of test samples.

### Tech stack
Application written in Java.

Technologies:
- jdk17
- gradle 8.2.1
- spring boot
- groovy & spock - for testing
- docker - for test containers
- jacoco - code coverage
- pmd - static code analysis
- swagger - api documentation

### Starting application
Run ``` ./gradlew bootRun```

This command will start
- postgres db as docker container, on port 15432
- spring boot application on port 8080

#### Starting with params
Example ```./gradlew bootRun --args='--server.port=8081'```

Possible params:
- `server.port`, default: `8080` - port for application
- `spring.datasource.url`, default: `jdbc:postgresql://localhost:15432/lab_storage` - jdbc url
- `spring.datasource.username`, default: `postgres`
- `spring.datasource.password`, default: `postgres`

#### Starting db as docker container
Requirements:
- docker

To start postgres db container
``` ./gradlew composeUp```

#### Stopping docker db container
Requirements:
- docker

To start postgres db container
``` ./gradlew composeDown```

### Running tests
Requirements: 
- JDK 17
- docker - for test containers

Run `./gradlew clean check`

### API documentation
Run application and open swagger ui http://localhost:8080/swagger-ui/index.html

### CI
Tests running on gitlab for every commit. [here](https://gitlab.com/lab-job-interview/laboratory-storage/-/pipelines)

### TODO:
- parametrized docker compose
