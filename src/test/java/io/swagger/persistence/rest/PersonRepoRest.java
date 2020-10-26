package io.swagger.persistence.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.persistence.entity.Person;
import io.swagger.persistence.repo.PersonRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static util.TestUtility.fromStreamToString;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonRepoRest {
    private static final Logger LOGGER = LogManager.getLogger(PersonRepoRest.class);
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PersonRepo personRepo;

    @Test
    @DisplayName("find-All-By-FullName-Containing-IgnoreCase")
    void givenMockMvc_whenInvokingPersonRepo_thenReturnSuccess_withPersonMockedObject() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/test-case.json");
        String jsonStr = fromStreamToString(resource.getInputStream());
        LOGGER.info("Test-Case: \n{}", jsonStr);

        Person testUnsavedPerson = objectMapper.readValue(jsonStr, Person.class);
        List<Person> personList = new ArrayList<>();
        personList.add(testUnsavedPerson);
        when(personRepo.findAllByFullNameContainingIgnoreCase(anyString())).thenReturn(personList);

      MvcResult  mvcResult =  mockMvc.perform(get("/api=false/people/search/findAllByFullNameContainingIgnoreCase?fullName=Se")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.people").isArray())
                .andExpect(jsonPath("$._embedded.people", hasSize(1)))
                .andExpect(jsonPath("$._embedded.people[0].fullName", is("Sean")))
                .andExpect(jsonPath("$._embedded.people[0].familyName", is("Huni")))
                .andReturn();

      LOGGER.info("Response: \n{}", mvcResult.getResponse().getContentAsString());
    }

}
