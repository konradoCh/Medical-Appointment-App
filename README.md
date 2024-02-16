# Medical Appointment App

_An application for patients and doctors. 
It allows for creating doctors' available dates and confirming them by  
adding notes and describing illnesses.
Moreover, the app permits to schedule appointment with specific 
doctor, cancelling reservations and checking upcoming, canceled and completed appointments.
along doctor's description. _


## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [ERD Diagram](#erd-diagram)
* [Screenshots](#screenshots)
* [Tests](#tests)
* [API](#api)
* [Setup](#setup)
* [Usage](#usage)
* [Project Status](#project-status)
* [Room for Improvement](#room-for-improvement)
* [Contact](#contact)

## General Information
- Provide general information about your project here.
- What problem does it (intend to) solve?
- What is the purpose of your project?
- Why did you undertake it?
<!-- You don't have to answer all the questions - just the ones relevant to your project. -->


## Technologies Used
- Spring Boot - version 3.2.1
- SpringDoc OpenAPI Starter WebMVC UI - version 2.3.0
- Bootstrap - version 5.2.2
- Wiremock - version 3.0.1
- Rest Assured - version 5.4.0
- Swagger Annotations - version 1.6.13
- Mapstruct - version 1.5.5.Final
- Lombok, Logback, JUnit, Mockito, Flyway, Hibernate
- Postgresql
- Thymeleaf
- OpenApi
- Docker

## Features
List the ready features here:
* Security:
    - login or creating new user
* Patient:
    - Schedule appointment
    - Display available doctor specialization
    - Checking patients' upcoming, canceled and completed appointments
    - Checking description connected with completed appointment
    - Cancel reservation
* Doctor:
    - Adding and canceling term of appointment
    - Checking reserved appointment
    - Checking patients' illness history
    - Adding notes and describing illnesses to the appointment



## ERD Diagram
<p align="center">
<img src="./images/diagram.jpeg" width=1000>
</p>

## Screenshots

| <p align="center"><img src="./images/login.jpg" width="400" /></p> |
|---|
| <p align="center"><b>Login screen</b></p> |


| <p align="center"><img src="./images/newUser.jpg" width="400" /></p> |
|---|
| <p align="center"><b>Create New User</b></p> |


| <p align="center"><img src="./images/home.jpg" width="1000" /></p> |
|---|
| <p align="center"><b>Home page</b></p> |


| <p align="center"><img src="./images/patientPortal.jpg" width="1000" /></p> |
|---|
| <p align="center"><b>Patient portal</b></p> |


| <p align="center"><img src="./images/upcoming.jpg" width="1000" /></p> |
|---|
| <p align="center"><b>Upcoming appointments</b></p> |

## Tests
It has been prepared tests covering **more than 80 %** of the code lines.

The following types of tests have been prepared:
- Unit tests (Mockito)
- DataJpaTest
- WebMvcTest
- SpringBootTest
- RestAssured
- Wiremock

## API
There is a prepared REST API which includes GET, POST, PUT, and DELETE endpoints. 
It allows users to manage available appointments for doctors.
Moreover, it consuming external api - random-data-api.com



## Setup
What are the project requirements/dependencies? Where are they listed? A requirements.txt or a Pipfile.lock file perhaps? Where is it located?

Proceed to describe how to install / setup one's local environment / get started with the project.


## Usage
How does one go about using it?
Provide various use cases and code examples here.

`write-your-code-here`


## Project Status
Project is: _complete_


## Room for Improvement
Include areas you believe need improvement / could be improved. Also add TODOs for future development.

Room for improvement:
- Improving the visual aspects of the application 
- Patients' ability to add opinion after appointment 


## Contact
Created by [@konradoCh](chochorowski.konrad@gmail.com) 
