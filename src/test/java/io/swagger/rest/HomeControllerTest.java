package io.swagger.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test Root URL-Redirect")
    void givenMockMvc_whenInvokingHomeController_thenReturnSuccess_withSwaggerRedirect() throws Exception {

        mockMvc.perform(get("/")
                .contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("swagger-ui/"))
                .andReturn();
    }
}