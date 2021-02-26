# Hexagonal-address

[![CircleCI](https://circleci.com/gh/Jdemon/hexagonal-address.svg?style=shield&circle-token=bf6040f6f3d1068c3b2fcadf783f4daf15a0d5d6)](https://circleci.com/gh/Jdemon/hexagonal-address)


## Requirements

For building and running the application you need:

- [Open JDK 11](https://openjdk.java.net/projects/jdk/11/)
- [Maven 3](https://maven.apache.org)
- [Redis](https://redis.io/)
- [Docker Desktop](a)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `th.hexagonal.address.Application` class from your IDE.

run redis first.

```
docker-compose up -d
```

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn clean spring-boot:run
```


## Build docker image

```shell
mvn clean spring-boot:build-image -DskipTests
```

