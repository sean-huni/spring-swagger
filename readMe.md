# Swagger Spring Boot Project

In light of some of the challenges experienced in most Java EE projects, this project have been built to express some 
formalities in the structure & manner in which restful APi's should be delivered to other teams. 

### Questions we aim to address
What are the standard CRUD(Create, Read, Update, Delete) operations of developing a Restful API?
How do we present it formally using HTTP Methods?
- HTTP.POST -> To create a new resource.
- HTTP.GET -> To retrieve an existing resource.
- HTTP.PUT -> To update an entire resource.
- HTTP.PATCH -> To perform a partial update to an existing resource.
- HTTP.DELETE -> delete an existing resource.

1. ##### How can we use Spring Data Rest to create operations that enable the above?
2. ##### How can we secure(with Spring Security) our restful API & protect it from unauthorised access?
3. ##### How can we enforce validations to the incoming request-payload to avoid incomplete/corrupt data to be saved in the Database?
4. ##### How do we adopt a TDD (Test Driven Development) approach to write good quality tests?
5. ##### How do we write tests for Spring Data Rest, Spring Security, Defensive Tests, Positive Scenarios Tests?

## Launching The Project
There are various ways of launching the project. Either from an IDE(Integrated Development Environment) or from the Terminal.

### IntelliJ IDE
Once the project has been recognized as a Spring Boot Java Project navigate to the following class path:
 src >> java >> io >> swagger >> SpringSwaggerApplication.java

Right click & run the SpringSwaggerApplication.java

### Unix Terminal
Current working directory: Project root.
Execute the following to launch the project:

 - On Unix/MacOS
 
    `./gradlew bootRun`
    
 - On Windows:

    `gradlew.bat run`
    
Ctrl+C to terminate the application in execution.

### Next Up!!! The Browser
* Url where the local-app is running: [http://localhost:8082/customer-service](http://localhost:8082/customer-service)
 
### Executing the Tests
     
* On Unix/MacOS:
    
    `sh gradlew test`
       
* On Windows: 
    
    `gradlew.bat test`
 
# Restful API Swagger-Documentation
Once the application has been launched successfully, it's now time to play with it.
* [API Reference Documentation](http://localhost:8082/customer-service)
Click on Person's Entity to Expand the operations of th API.

### Additional Links & References
* [Best Practices for Restful API Design](https://avaldes.com/best-practices-for-restful-api-design/)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.3.5.RELEASE/reference/htmlsingle/#boot-features-security)
* [Spring Data Security Examples](https://github.com/spring-projects/spring-data-examples)

# Future Work
Part of making developing our Restful API. It is critical our API is versioned correctly for future changes & releases.
What are the Best Practices of Versioning our Restful API?
* [Thread on REST API Versioning with Spring](https://stackoverflow.com/questions/20198275/how-to-manage-rest-api-versioning-with-spring)

# About the Author
* [Sean's Resume](https://sean-huni.xyz)
