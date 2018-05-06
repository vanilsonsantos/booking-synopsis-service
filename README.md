# booking-synopsis-service

 * This is a sample application service demonstrating how to integrate VoiceBunny api with other external services when booking a new project at voicebunny.com.

# Pre-configuration
 * Set the following environment variable JASYPT_ENCRYPTOR_PASSWORD=od5ct2xec914utsm

# Dependency management 
 * The project was built using gradle, but no need to install it, a local wrapper is already included on the solution

# Running tests locally
 * ./gradlew -Dspring.profiles.active=staging test
 * Testing frameworks used: Junit, Mockmvc, Wiremock

# Running application locally
 * ./gradlew -Dspring.profiles.active=staging bootrun

# Endpoints

Status:
 - GET: https://booking-synopsis-service.herokuapp.com/booking/actuator/health
 
Info:
 - GET: https://booking-synopsis-service.herokuapp.com/booking/actuator/info
 
Book Synopsis:
- POST: https://booking-synopsis-service.herokuapp.com/booking/synopsis
```
{
  "movieName": "Childs's play"
}
```
