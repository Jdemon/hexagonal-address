# Hexagonal-address

[![CircleCI](https://circleci.com/gh/Jdemon/hexagonal-address.svg?style=shield&circle-token=bf6040f6f3d1068c3b2fcadf783f4daf15a0d5d6)](https://circleci.com/gh/Jdemon/hexagonal-address)


## Requirements

For building and running the application you need:

- [Open JDK 11](https://openjdk.java.net/projects/jdk/11/)
- [Maven 3](https://maven.apache.org)
- [Redis](https://redis.io/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop)

## Flows

```sequence
 client --> Hexagonal-address --> json-data-filter
```

https://github.com/Jdemon/fastify-json-data-filter

## APIs Doc

## Address API [/address/{localization}?postalCode={postalCode}&subDistrictKey={subDistrictKey}&districtKey={districtKey}&provinceKey={provinceKey}]

### GET DATA [GET]

+ Path Parameters

    + localization: `th` (string, mandatory) - `th` or `en`.

+ Parameters

    + postalCode: `20000` (number, mandatory)
    + subDistrictKey: `2001` (string, optional)
    + districtKey: `2001` (string, optional)
    + provinceKey: `20` (string, optional)
    
+ Response 200 (application/json)

    + Body

            {
                "localization":"th",
                "postalCode":20000,
                "subDistricts":[
                    {
                        "key":"200102",
                        "value":"มะขามหย่ง"
                    }
                ],
                "districts":[
                    {
                        "key":"2001",
                        "value":"เมืองชลบุรี"
                    }
                ],
                "provinces":[
                    {
                        "key":"20",
                        "value":"ชลบุรี"
                    }
                ]
            }


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

