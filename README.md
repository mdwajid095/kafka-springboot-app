# Kafka Microservices with Spring Boot

A modular, profile-driven Spring Boot project for building Kafka admin, producer, consumer and stream microservices with clean separation and easy configuration.

---

## Table of Contents

* [How to use](#how-to-use)
* [Version Details](#version-details)
* [Service Overview](#service-overview)
* [Project Structure](#project-structure)
* [Common Commands](#common-commands)

---

## How to use

As in [Service Overview](#service-overview) we can see, we have several services inside this `kafka-springboot-app` application. So, to invoke the particular service, we have to pass the profiles in `application.yaml` file. Like, to invoke consumer service, we have to set profiles as `consumer`.

- Admin service profiles is `admin`
- Consumer service profiles is `consumer`
- Producer service profiles is `producer`
- Streams service profiles is `stream`

---

## Version Details

- **Java:** 17+ (recommended)
- **Spring Boot:** 3.x
- **Spring Kafka:** 3.x
- **Apache Kafka Client:** 3.9.1 (override in `pom.xml` if needed)
- **Maven:** 3.8+ (or use included `mvnw` wrapper)

---

## Service Overview

- **KafkaApplication**  
  *Purpose:* The main Spring Boot application entry point.


- **Admin**  
  *Purpose:* Provides endpoints and logic for Kafka admin tasks such as creating topics dynamically.  
  *Profile:* `admin`  
  *Usage:* Use this service to manage Kafka topics at runtime via REST endpoints.


- **Consumer**  
  *Purpose:* Listens to Kafka topics and processes consumed messages.  
  *Profile:* `consumer`  
  *Usage:* Processes messages from Kafka topics as they arrive.


- **Producer**  
  *Purpose:* Handles publishing messages/events to Kafka topics.  
  *Profile:* `producer`  
  *Usage:* Exposes REST endpoints for sending events to Kafka.


- **Streams**  
  *Purpose:* Handles streaming messages/events from/to Kafka topics.  
  *Profile:* `stream`  
  *Usage:* Streams messages from Kafka topics as they arrive and send to terger topic.

---

## Project Structure

```
kafka/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── kafka/
│   │   │           ├── KafkaApplication.java
│   │   │           ├── admin/
│   │   │           │   ├── controller/
│   │   │           │   │   └── KafkaAdminController.java
│   │   │           │   └── service/
│   │   │           │       └── KafkaAdminService.java
│   │   │           ├── producer/
│   │   │           │   ├── controller/
│   │   │           │   │   └── KafkaProducerController.java
│   │   │           │   └── service/
│   │   │           │       └── KafkaProducerService.java
│   │   │           ├── consumer/
│   │   │           │   ├── controller/
│   │   │           │   │   └── KafkaConsumerController.java
│   │   │           │   └── service/
│   │   │           │       └── KafkaConsumerService.java
│   │   │           └── .. (other services)
│   │   └── resources/
│   │       ├── application.yaml
│   │       └── certs/
│   └── test/
│       └── java/
│           └── com/
│               └── kafka/
│                   └── .. (test classes)
├── pom.xml
└── README.md
```

---

## Common Commands

### Kill used port (Windows)
```sh
netstat -aon | findstr :8080
taskkill /PID <PID> /F
```

### Maven build
```sh
./mvnw clean package
```

### Check version of kafka-client
```sh
./mvnw dependency:tree
```

### Override kafka-client version in `pom.xml`
```xml
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-clients</artifactId>
    <version>3.9.1</version> <!-- or your desired version -->
</dependency>
```

### Run application with profile
```sh
java -jar target/consumer-0.0.1-SNAPSHOT.jar --spring.profiles.active=admin
java -jar kafka-app.jar --spring.config.location=file:/tmp/application.yaml --spring.profiles.active=consumer
java "-Dhttp.proxyHost=10.169.127.8" "-Dhttp.proxyPort=8080" "-Dhttps.proxyHost=10.169.127.8" "-Dhttps.proxyPort=8080" -jar target/consumer-0.0.1-SNAPSHOT.jar
```

### Format code with Maven
```sh
./mvnw fmt:format
```

### Run tests
```sh
./mvnw test
```

### Clean and remove target directory
```sh
./mvnw clean
```

### List all running Java processes (to find PID)
```sh
jps -l
```

---
