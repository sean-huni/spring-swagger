package io.swagger.persistence.rest.defensive;

import io.swagger.ext.logic.aop.PersonRepoAopTestMock;
import io.swagger.persistence.entity.Person;
import io.swagger.persistence.repo.PersonRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import static io.swagger.commons.Constant.ROLE_SERVICE;
import static io.swagger.ext.util.TestUtility.fromStreamToString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableAspectJAutoProxy
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"dev", "dev-2"})
@Import(PersonRepoAopTestMock.class)
public class ConstraintViolationTest {
    private static final String ROOT_URI = "/api=false";
    @Autowired
    private ResourceLoader resourceLoader;
    @Mock
    private PersonRepo personRepo;
    @MockBean
    private ConstraintViolation<Person> personConstraintViolation;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "spring", roles = ROLE_SERVICE)
    @DisplayName("Save an Erroneous Person - HTTP.POST")
    void givenMockMvc_whenSavingPerson_thenThrowConstraintViolationException() throws Exception {
        when(personRepo.save(any(Person.class))).thenThrow(new ConstraintViolationException("Testing the Mocked PersonRepo", null));

        Resource resource = resourceLoader.getResource("classpath:json/test-case.json");
        String jsonStr = fromStreamToString(resource.getInputStream());
        jsonStr = jsonStr.replace("Parkinson", "");

        mockMvc.perform(post(ROOT_URI + "/people")
                .contentType("application/json;charset=UTF-8")
                .content(jsonStr))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.field", is("fullName")))
                .andExpect(jsonPath("$.status", is("403")))
                .andExpect(jsonPath("$.reason", is("length must be between 1 and 255")))
                .andExpect(jsonPath("$.message", is("Please check the input-values in your request-payload and try again.")))
                .andExpect(jsonPath("$.referenceError", is(ROOT_URI + "/people")))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException));

        verify(personRepo, times(0)).save(any());
    }
}
