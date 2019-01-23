[![Quality Status](https://sonarcloud.io/api/project_badges/measure?project=net.bakaar%3Amicroservice-sandbox&metric=alert_status)](https://sonarcloud.io/api/project_badges/measure?project=net.bakaar%3Amicroservice-sandbox&metric=alert_status)
[![Code coverage](https://sonarcloud.io/api/project_badges/measure?project=net.bakaar%3Amicroservice-sandbox&metric=coverage)](https://sonarcloud.io/api/project_badges/measure?project=net.bakaar%3Amicroservice-sandbox&metric=coverage)
# Microservice Sandbox 

This Sandbox use a simplified version of an Insurance Claim System.

## Architecture Diagram
Those diagrams are freely inspired from the C4 model from Simon Brown.

### Systems

![Systems Diagram](./doc/images/systems.png)

### Components

![Components Diagram](./doc/images/components.png)

## Use Cases

- Create a Partner in the dedicated system
- Create a Case linked to a Partner, the Case should received a Business Number.
- When a Case is created, a Task should appear to remind me to treat the Case.
- Change the Business ID of a Partner (the change should cascade to the Claim domain).
- Interrogate the Claim system and show a Case with all the information of the related partner.
- Once the Case closed, the related Task should closed too (event).
- When I change the birth date of a partner with a Case, a Task should appear to force me to treat this case again.

### NFRs
- Every call should be tracked with a CorrelationID.
- Every sent event could be send again, replayable.
- All system should be idempotent (multiple identical messages delivery).
- All error should be formatted in a way every microservices understand it.

## Technological show cases

* Synchronous call with circuit breaker.
* Long business transaction through multiple microservices enduring failure (Sagas).
* Synchronous call with retry.
* Communication with messages (Choreography/Broker).
* Communication with messages (Polling).

## TODO's

- [x] Make a todo list ;-)
- [x] Implement VO's for insured and PNummer
- [x] Add TravisCI configuration
- [x] Add SonarQube analysis to the build
- [x] Add Spring-Boot Application to Case to run it and test via rest interface
- [x] Create docker-compose file to run Case and its DB inside containers
- [x] Implement Business Number provider
- [x] Make Business Service running in a container
- [x] Fix build travis
- [x] Create Partner service
- [x] Create a Person frontend with Angular, Jest tested and CDC test with pact
- [x] Add frontend build to the maven build of person
- [ ] Make all the docker image deploy to DockerHub
- [ ] Refactor all the docker compose yaml files to use Dockerhub images
- [ ] Update Spring-Boot version for all project
- [ ] Add error to Rest API test and have a standard to propagate this error
- [x] Add pact test in infra-data-rest for BusinessNumber endpoint
- [x] Add an endpoint in BNS to return PNumber format id
- [ ] Add birth date in person api signature
- [ ] Add partner info (only birthdate, other info should be read each time) to Case microservice and enable update threw messaging
- [ ] Add Sonar analysis of person-frontend typescript the report on SonarCloud
- [ ] Create screens for person : search, list and create and corresponding Rest API
- [ ] Secure the creation of a partner behind a role and protect the endpoint
- [ ] Make person Service running in a container
- [x] Add partner info (Name, Forename, BirthDate)
- [x] Refactor Case architecture to the Person one
- [ ] Create the claim link service
- [ ] Create polling service
- [ ] Create a base module (commons?) gathering all the interfaces and base classes 
- [ ] Add call context for all rest calls
- [ ] Add Call context for all logging
- [ ] Add a message broker (Kafka)
- [ ] Put in place the calls tracking system (Jhipster console)
- [ ] Finish messaging implementation (including Call Context)
- [ ] Create the task service (link with messages)
- [ ] Make the partner changes cascade to Case
- [ ] Make the documentation with Structurizr
- [ ] Implement ArchUnit Test
- [ ] Prevent Business Number Service to create twice the same partner id

## Things to explore
* Service discovery with Spring Cloud Eureka
* Call tracking with :
  * Zipkin
  * Istio
  * Jhipster console
* Security with Spring Security
  * JWT
* Use Kubernetes to deploy all the components
  * Use Secret in Kubernetes
  * Use Helm ?
* Use Flyway and Liquibase to migrate database
* Use different types of database corresponding to the needs of the microservice
* Migrate to Kotlin
* Migrate to Spring-Boot 2
  * Use Async Webflux
* Testing
  * Pact
  * HoverFly or Wiremock
* Monitor
  * Metrics
  * JHispter Console
* Deployment
  * Use DockerHub to deploy Container
  * Use Minikub in local to Deploy
  * Jenkins pipeline as Code
* Use Hibernate Search in Partner to find partner by name or forename

## Open Points
* Should we use Factory as in case or domain service as in person to create domain object ? !!SRP!!
