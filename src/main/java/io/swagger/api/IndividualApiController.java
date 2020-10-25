package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.model.Individual;
import io.swagger.model.IndividualCreate;
import io.swagger.model.IndividualUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-08T11:42:54.708Z")

//@SuppressWarnings("unchecked")
@Controller
public class IndividualApiController implements IndividualApi {

    private static final Logger log = LoggerFactory.getLogger(IndividualApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public IndividualApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Individual> createIndividual(@ApiParam(value = "The Individual to be created", required = true) @Valid @RequestBody IndividualCreate individual) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Individual>(objectMapper.readValue("{  \"aristocraticTitle\" : \"aristocraticTitle\",  \"gender\" : \"gender\",  \"@baseType\" : \"@baseType\",  \"countryOfBirth\" : \"countryOfBirth\",  \"@type\" : \"@type\",  \"familyName\" : \"familyName\",  \"deathDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"fullName\" : \"fullName\",  \"id\" : 1,  \"href\" : \"href\",  \"@schemaLocation\" : \"http://example.com/aeiou\",  \"birthDate\" : \"2000-01-23T04:56:07.000+00:00\"}", Individual.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Individual>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Individual>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteIndividual(@ApiParam(value = "Identifier of the Individual", required = true) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Individual>> listIndividual(@ApiParam(value = "Comma-separated properties to be provided in response") @Valid @RequestParam(value = "fields", required = false) String fields, @ApiParam(value = "Requested index for start of resources to be provided in response") @Valid @RequestParam(value = "offset", required = false) Integer offset, @ApiParam(value = "Requested number of resources to be provided in response") @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Individual>>(objectMapper.readValue("[ {  \"aristocraticTitle\" : \"aristocraticTitle\",  \"gender\" : \"gender\",  \"@baseType\" : \"@baseType\",  \"countryOfBirth\" : \"countryOfBirth\",  \"@type\" : \"@type\",  \"familyName\" : \"familyName\",  \"deathDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"fullName\" : \"fullName\",  \"id\" : 1,  \"href\" : \"href\",  \"@schemaLocation\" : \"http://example.com/aeiou\",  \"birthDate\" : \"2000-01-23T04:56:07.000+00:00\"}, {  \"aristocraticTitle\" : \"aristocraticTitle\",  \"gender\" : \"gender\",  \"@baseType\" : \"@baseType\",  \"countryOfBirth\" : \"countryOfBirth\",  \"@type\" : \"@type\",  \"familyName\" : \"familyName\",  \"deathDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"fullName\" : \"fullName\",  \"id\" : 1,  \"href\" : \"href\",  \"@schemaLocation\" : \"http://example.com/aeiou\",  \"birthDate\" : \"2000-01-23T04:56:07.000+00:00\"} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Individual>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Individual>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Individual> patchIndividual(@ApiParam(value = "Identifier of the Individual", required = true) @PathVariable("id") String id, @ApiParam(value = "The Individual to be updated", required = true) @Valid @RequestBody IndividualUpdate individual) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Individual>(objectMapper.readValue("{  \"aristocraticTitle\" : \"aristocraticTitle\",  \"gender\" : \"gender\",  \"@baseType\" : \"@baseType\",  \"countryOfBirth\" : \"countryOfBirth\",  \"@type\" : \"@type\",  \"familyName\" : \"familyName\",  \"deathDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"fullName\" : \"fullName\",  \"id\" : 1,  \"href\" : \"href\",  \"@schemaLocation\" : \"http://example.com/aeiou\",  \"birthDate\" : \"2000-01-23T04:56:07.000+00:00\"}", Individual.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Individual>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Individual>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Individual> retrieveIndividual(@ApiParam(value = "Identifier of the Individual", required = true) @PathVariable("id") String id, @ApiParam(value = "Comma-separated properties to provide in response") @Valid @RequestParam(value = "fields", required = false) String fields) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Individual>(objectMapper.readValue("{  \"aristocraticTitle\" : \"aristocraticTitle\",  \"gender\" : \"gender\",  \"@baseType\" : \"@baseType\",  \"countryOfBirth\" : \"countryOfBirth\",  \"@type\" : \"@type\",  \"familyName\" : \"familyName\",  \"deathDate\" : \"2000-01-23T04:56:07.000+02:00\",  \"fullName\" : \"fullName\",  \"id\" : 1,  \"href\" : \"href\",  \"@schemaLocation\" : \"http://example.com/aeiou\",  \"birthDate\" : \"2000-01-23T04:56:07.000+02:30\"}", Individual.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Individual>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Individual>(HttpStatus.NOT_IMPLEMENTED);
    }

}
