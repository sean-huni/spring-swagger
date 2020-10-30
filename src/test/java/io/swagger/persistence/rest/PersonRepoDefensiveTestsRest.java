package io.swagger.persistence.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static util.TestUtility.fromStreamToString;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PersonRepoDefensiveTestsRest {
    private static final String ROOT_URI = "/api=false";
    private static final Logger LOGGER = LogManager.getLogger(PersonRepoDefensiveTestsRest.class);
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    @DisplayName("Create-Failing Person ")
    @WithMockUser(username = "spring", roles = "SERVICE")
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
