# Example project for automated testing of a Java EE 7 application

This Java project shows how to test each individual component of a Java EE 7 application. It uses many different frameworks for testing each component in isolation. On top of that there are also integration tests on a real application server. The project is built with [Gradle](https://gradle.org/ "Gradle"). These frameworks and tools are used:

*   POJOs: JUnit 5, Spock, JUnit-QuickCheck, Bean Matchers
*   Stubbing/Mocking: Mockito, PowerMock
*   JPA: Apache Derby, EclipseLink
*   Bean Validation: Hibernate Validator
*   JAX-RS: Jetty, Jersey, REST-assured
*   CDI/JSF: Arquillian, PhantomJS, Selenium, Arquillian Drone, Arquillian Graphene, Arquillian Chameleon
*   external REST-API: WireMock
*   Mutation testing: PITest

## Prerequisites

For running the integration tests with Arquillian, a [Wildfly application server](http://wildfly.org/ "Wildfly") has to be running and a management user has to be available. You can configure the deployment details in `src/integrationTest/resources/arquillian.xml`. The defaults are:

*   Target server: Wildfly 10
*   Hostname: localhost
*   Port: 9990
*   Username: admin
*   Password: admin

## Get started

*   For creating an Eclipse project run `./gradlew eclipse`
*   For a full build including all unit and integration tests run `./gradlew build`.
*   For only the unit tests run `./gradlew test`.
*   For all unit and integration tests run `./gradlew integrationTest`.
