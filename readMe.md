# Swagger Spring Boot Project

In light of some of the challenges experienced in most Java EE projects, this project have been built to express some 
formalities in the structure & manner in which restful APi's should be delivered to other teams. 

### Questions we aim to address
What are the standard CRUD(Create, Read, Update, Delete) operations of developing a Restful API that stipulated?
How do we present it formally using HTTP Methods?
- HTTP.POST -> To create a new resource.
- HTTP.GET -> To retrieve an existing resource.
- HTTP.PUT -> To update a resource.
- HTTP.PATCH -> To perform a partial update to an existing resource.
- HTTP.DELETE -> delete an existing resource.

#### How can we use Spring Data Rest to create operations that enable the above?
#### How can we secure(with Spring Security) our restful API & protect it from unauthorised access?
#### How can we enforce validations to the incoming request-payload to avoid incomplete/corrupt data to be saved in the Database?
#### How do we adopt a TDD (Test Driven Development) approach to write good quality tests?
#### How do we write tests for Spring Data Rest, Spring Security, Defensive Tests, Positive Scenarios Tests?

## Launching The project
There are various ways of launching the project. Either from an IDE(Integrated Development Environment) or from the Terminal.

### IntelliJ IDE
Once the project has been recognized as a Spring Boot Java Project navigate to the following class path:
 src >> java >> io >> swagger >> SpringSwaggerApplication.java

Right click & run the SpringSwaggerApplication.java

### Unix Terminal
Current working directory: Project root.
Execute the following to launch the project:

    ./gradlew bootRun

Ctrl+C to terminate the application in execution.