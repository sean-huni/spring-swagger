package io.swagger.persistence.rest.positive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static io.swagger.commons.Constant.ROLE_SERVICE;
import static io.swagger.ext.util.TestUtility.fromStreamToString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonRepoRestPositiveTest {
    private static final String ROOT_URI = "/api=false";
    private static final Logger LOGGER = LogManager.getLogger(PersonRepoRestPositiveTest.class);
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "spring", roles = ROLE_SERVICE)
    @DisplayName("find-All-By-FullName-Containing-IgnoreCase - HTTP.GET")
    void givenMockMvc_whenInvokingFindAllByFullNameContainingIgnoreCase_thenReturnSuccess_withPersonDatabaseObject() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ROOT_URI + "/people/search/findAllByFullNameContainingIgnoreCase?fullName=Se")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.people[0].fullName", is("Sean")))
                .andExpect(jsonPath("$._embedded.people[0].familyName", is("Huni")))
                .andReturn();

        LOGGER.info("Response: \n{}", mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "spring", roles = ROLE_SERVICE)
    @DisplayName("Save New Person - HTTP.POST")
    @DirtiesContext
    void givenMockMvc_whenSavingNewPerson_thenReturnIsCreated_withRedirectedUrlToNewPersonObject() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/test-case.json");
        String jsonStr = fromStreamToString(resource.getInputStream());
        LOGGER.info("Test-Case: \n{}", jsonStr);

        mockMvc.perform(post(ROOT_URI + "/people")
                .contentType("application/json")
                .content(jsonStr))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(redirectedUrl("http://localhost/api=false/people/2"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "spring", roles = ROLE_SERVICE)
    @DisplayName("Update an Existing Person - HTTP.PATCH")
    @DirtiesContext
    void givenMockMvc_whenUpdatingAnExistingPerson_thenReturnIsCreated_withRedirectedUrlToNewPersonObject() throws Exception {
        String jsonStr = "{\"countryOfBirth\": \"England\", \"familyName\": \"Parkinson\",\"fullName\": \"Queen\",\"gender\": \"FEMALE\"}";
        LOGGER.info("Test-Case: \n{}", jsonStr);

        mockMvc.perform(patch(ROOT_URI + "/people/1")
                .contentType("application/json")
                .content(jsonStr))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "spring", roles = ROLE_SERVICE)
    @DisplayName("Find Existing Person - HTTP.GET")
    void givenMockMvc_whenFetchingPerson_thenReturnExistingPerson_withSuccess() throws Exception {
        mockMvc.perform(get(ROOT_URI + "/people/1")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", is("Sean")))
                .andExpect(jsonPath("$.familyName", is("Huni")))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "spring", roles = ROLE_SERVICE)
    @DisplayName("Delete An Existing Person - HTTP.DELETE")
    @DirtiesContext
    void givenMockMvc_whenDeletingPerson_thenSucceed() throws Exception {
        mockMvc.perform(delete(ROOT_URI + "/people/1")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
