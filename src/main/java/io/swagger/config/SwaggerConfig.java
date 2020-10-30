package io.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-08T11:42:54.708Z")

@Configuration
public class SwaggerConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Sample Indentity in Project")
                .description("The identity API provides standardized mechanism for identity management such as creation, update, retrieval, deletion. Party can be an individual or an organization that has any kind of relation with the enterprise.   ### Resources - Individual  Party API performs the following operations : - Retrieve an individual - Retrieve a collection of individuals according to given criteria - Create a new individual - Update an existing individual - Delete an existing individual")
                .license("")
                .licenseUrl("http://unlicense.org")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("Sean Huni", "https://sean-huni.xyz", "sean2kay@gmail.com"))
                .build();
    }

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .tags(new Tag("Person Entity", "Repository for People's entities"))
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .apis(not(RequestHandlerSelectors.basePackage("io.swagger.rest")))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private static Predicate<RequestHandler> not(Predicate<RequestHandler> requestHandlerPredicate) {
        return input -> !requestHandlerPredicate.test(input);
    }
}
