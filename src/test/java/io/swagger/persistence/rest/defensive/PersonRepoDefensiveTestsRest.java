package io.swagger.persistence.rest.defensive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static io.swagger.ext.util.TestUtility.fromStreamToString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonRepoDefensiveTestsRest {
    private static final String ROOT_URI = "/api=false";
    private static final Logger LOGGER = LogManager.getLogger(PersonRepoDefensiveTestsRest.class);
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Prevent Create Person with Unauthorised Access")
    void givenNoMockedUser_whenCreatingAPerson_thenThrowException_andReturnClientUnauthorisedAccessErrorMessage() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/test-case.json");
        String jsonStr = fromStreamToString(resource.getInputStream());

        LOGGER.info("Test-Case: \n{}", jsonStr);

        mockMvc.perform(post(ROOT_URI + "/people")
                .contentType("application/json")
                .content(jsonStr))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", is("Unauthorized")))
                .andExpect(jsonPath("$.status", is("401")))
                .andExpect(jsonPath("$.reason", is("An Authentication object was not found in the SecurityContext")))
                .andExpect(jsonPath("$.message", is("Unauthorised Access. Please login and try again.")))
                .andExpect(jsonPath("$.referenceError", is(ROOT_URI + "/people")));
    }

    @Test
    @DisplayName("Create Person with Validations Errors")
    @WithMockUser(username = "spring", roles = "SERVICE")
    void givenMockMvc_whenCreatingAPerson_thenThrowException_andReturnClientErrorMessage() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/test-case.json");
        String jsonStr = fromStreamToString(resource.getInputStream());
        jsonStr = jsonStr.replaceAll("Queen", "");
        LOGGER.info("Test-Case: \n{}", jsonStr);

        mockMvc.perform(post(ROOT_URI + "/people")
                .contentType("application/json")
                .content(jsonStr))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is("Forbidden")))
                .andExpect(jsonPath("$.status", is("403")))
                .andExpect(jsonPath("$.field", is("fullName")))
                .andExpect(jsonPath("$.reason", is("length must be between 1 and 255")))
                .andExpect(jsonPath("$.message", is("Please check the input-values in your request-payload and try again.")))
                .andExpect(jsonPath("$.referenceError", is(ROOT_URI + "/people")));
    }

    @Test
    @WithMockUser(username = "spring", roles = "SERVICE")
    @DisplayName("Update a non-Existing Person - HTTP.PATCH")
    @DirtiesContext
    void givenMockMvc_whenUpdatingAnExistingPerson_thenReturnIsCreated_withRedirectedUrlToNewPersonObject() throws Exception {
        String jsonStr = "{\"countryOfBirth\": \"England\", \"familyName\": \"Parkinson\",\"fullName\": \"Queen\",\"gender\": \"Female\"}";
        LOGGER.info("Test-Case: \n{}", jsonStr);

        mockMvc.perform(patch(ROOT_URI + "/people/100")
                .contentType("application/json")
                .content(jsonStr))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "spring", roles = "SERVICE")
    @DisplayName("Delete A Non-Existing Person - HTTP.DELETE")
    @DirtiesContext
    void givenMockMvc_whenDeletingPerson_thenSucceed() throws Exception {
        mockMvc.perform(delete(ROOT_URI + "/people/0")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
    }

}
