package io.swagger.persistence.rest;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static util.TestUtility.fromStreamToString;

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
    @DisplayName("find-All-By-FullName-Containing-IgnoreCase")
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
    @DisplayName("Save New Person")
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
    @DisplayName("Find Existing Person")
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
    @DisplayName("Delete An Existing Person")
    @DirtiesContext
    void givenMockMvc_whenDeletingPerson_thenSucceed() throws Exception {
        mockMvc.perform(delete(ROOT_URI + "/people/1")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

}
