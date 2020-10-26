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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static util.TestUtility.fromStreamToString;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonRepoNegativeTestRest {
    private static final String ROOT_URI = "/api=false";
    private static final Logger LOGGER = LogManager.getLogger(PersonRepoNegativeTestRest.class);
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Create-Failing Person ")
    @DirtiesContext
    void givenMockMvc_whenCreatingAPerson_thenThrowException_andReturnClientErrorMessage() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:json/test-case.json");
        String jsonStr = fromStreamToString(resource.getInputStream());
        jsonStr = jsonStr.replaceAll("Queen", "");
        LOGGER.info("Test-Case: \n{}", jsonStr);

        mockMvc.perform(post(ROOT_URI + "/people")
                .contentType("application/json")
                .content(jsonStr))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is("Bad Request")))
                .andExpect(jsonPath("$.status", is("400")))
                .andExpect(jsonPath("$.message", is("Please check the input-values in your request-payload and try again.")))
                .andReturn();
    }
}
