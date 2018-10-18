# CWDS API

The CWDS API provides RESTful services for the CWDS Modules.

## Wiki 

The development team is actively using the [Github Wiki](https://github.com/ca-cwds/API/wiki).

## News
For news that affect Developers and the app: [News](https://github.com/ca-cwds/API/wiki/News)

## Developer Topics

An [API Developer's Guide](https://github.com/ca-cwds/API/wiki/API-Developer's-Guide) is available.
For ongoing Developer discussion of topics about practices, standards, etc: [Developer Topics](https://github.com/ca-cwds/API/wiki/Developer-Topics)

## Commiting to Code Base
Code commits are submitted via pull request for authorized developers. Code is expected to confirm to Coding standards that include Coding practices, API Standards, best practices found at [Commiting Code](https://github.com/ca-cwds/API/wiki/Committing-Code).
Pull requests must include a filled out Pull Request Template and should contain information to help provide context around code. It must also include any special details or why something abnormal was done. If tests are not part of check in, then an explanation is expected.

## Documentation

The development team uses [Swagger](http://swagger.io/) for documenting the API.  
NOTE : At this time there is not a publicy available link to the documentation, a link will be provided as soon as one is available.

## Configuration

The CWDS API currently utilizes two persistent store:

1. DB2 - CWDS CMS database
2. Postgres - CWDS NS database

In order for the CWDS API successfully connect to the above databases the following environment variables are required to be set:

- DB_NS_USER -- the CWDS NS database username
- DB_NS_PASSWORD -- the CWDS NS database password
- DB_NS_JDBC_URL -- the CWDS NS database URL in Java Database Connectivity format
- DB_CMS_SCHEMA  -- the CWDS NS database schema the tables belong to.


- DB_CMS_USER -- the CWDS CMS database username
- DB_CMS_PASSWORD -- the CWDS CMS database password
- DB_CMS_JDBC_URL -- the CWDS CMS database URL in Java Database Connectivity format
- DB_CMS_SCHEMA -- the CWDS CMS database schema the tables belong to.
- DB_CMS_CP_INITIAL_SIZE -- the CWDS CMS database connection pool initial size
- DB_CMS_CP_MIN_SIZE -- the CWDS CMS database connection pool minimum size
- DB_CMS_CP_MAX_SIZE -- the CWDS CMS database connection pool maximum size


- DB_CWSRS_USER -- the CWDS RS database username
- DB_CWSRS_PASSWORD -- the CWDS RS database password
- DB_CWSRS_JDBC_URL -- the CWDS RS database URL in Java Database Connectivity format
- DB_CWSRS_SCHEMA -- the CWDS RS database schema the tables belong to.
- DB_CWSRS_CP_INITIAL_SIZE -- the CWDS RS database connection pool initial size
- DB_CWSRS_CP_MIN_SIZE -- the CWDS RS database connection pool minimum size
- DB_CWSRS_CP_MAX_SIZE -- the CWDS RS database connection pool maximum size


- LOGIN_URL -- The application login URL
- LOGOUT_URL -- The application logout URL


optional
- UPGRADE_DB_ON_START -- Runs liquidbase DB Update scripts
- SWAGGER_JSON_URL -- The JSON API Endpoint for Swagger
- SWAGGER_CALLBACK_URL -- The API Endpoint for Swagger
- SWAGGER_TOKEN_URL -- The login url for Swagger
- SHOW_SWAGGER -- enable Swagger
- LOGLEVEL -- The logging level for the application

The Docker env-file option provides a convenient method to supply these variables. These instructions assume an env file called .env located in the current directory. The repository contains a sample env file called env.sample.

Further configuration options are available in the file config/api.yml.

## Installation

### Prerequisites

1.  DB2 10.x
2.  Postgres 9.x

### Using Docker

The CWDS API is available as a Docker container on Dockerhub:

    https://hub.docker.com/r/cwds/api/

Run the application with Docker using a command like this:

    % docker run --env-file=.env -p 8080:8080 cwds/api

## Development Environment

### Prerequisites

1. Source code, available at [GitHub](https://github.com/ca-cwds/API)
1. Java SE 8 development kit
1. DB2 Database
1. Postgres Database
1. Docker ( if running a Database Docker Container )

### Database 

#### Docker - DB2
A [Docker Image](https://hub.docker.com/r/cwds/db2data/) with DB2 is available to develop against.

#### Docker - Postgres
A [Docker Image](https://hub.docker.com/r/cwds/postgresql_data/) with Postgress is available to develop against.  

### Development Server

Use the gradlew command to execute the tasks:

    % ./gradlew shadowJar
    % java -jar build/libs/api-dist.jar server config/api.yml

This will run the server on your local machine, port 8080.

### Unit Testing

Use the gradlew command to execute the test task:

    % ./gradlew test

### Integration Testing
Tests that access external resources such as the Database or that require the Dropwizard framework to
be spun up. These tests are often longer running tests and are therefore seperated from unit tests
for TDD purposes. The database utilize the src/test/resources/hibernate.cfg.xml configuration file.
Edit this file to utilize a local testing database.

Use the gradlew command to execute the test task:

    % ./gradlew integrationTest


### Functional Testing
Functional tests are tests that run against a complete environment. These are sometimes called QA or
Acceptance tests. Functional Tests cannot be ran without all the dependent systems running. These tests
are typically ran against a pre-production environment to verify both the code and the enviornment.


Functional tests have a configuration file that is defaulted to the config/testConfig.yml. Local
configurations are overwritten by creating a local copy on your disk and setting the TEST_FILE_PATH
environment variable to the file path.

Use the gradlew command to execute the functional task:

    % ./gradlew functionalTest

### Commiting Changes

Before commiting changes to the reporsitory please run the following to ensure the build is successful.

    % ./gradlew clean test integrationTest jacocoTestReport javadoc

### Building Docker Container

The continuous delivery server builds images for the container registry but developers can build local containers with
the following command:

    % docker build -t api .

This results in a local docker image in a repository called api with the tag latest.

# Questions

If you have any questions regarding the contents of this repository, please email the Office of Systems Integration at FOSS@osi.ca.gov.
