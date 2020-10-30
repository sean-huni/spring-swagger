package io.swagger.persistence.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.persistence.entity.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static util.TestUtility.fromStreamToString;

@SpringBootTest
class PersonRepoTest {
    private static final Logger LOGGER = LogManager.getLogger(PersonRepoTest.class);
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonRepo personRepo;

    @BeforeEach
    void setUp() {
        LOGGER.info(() -> "Executing new test");
    }

    @AfterEach
    void tearDown() {
        LOGGER.info(() -> "Finished previous test");
    }

    @Test
    @DisplayName("Find at least 1 Individual/person record in the Person-Table")
    @WithMockUser(username = "spring", roles = "SERVICE")
    void givenPersonRepo_returnRecordsInTable() {
        List<Person> personList = personRepo.findAll();

        assertNotNull(personList);
        assertEquals(1, personList.size());
    }

    @Test
    @WithMockUser(username = "spring", roles = "SERVICE")
    @DisplayName("Save at least 1 other Individual/person record in the Person-Table")
    @DirtiesContext
    void givenPersonRepo_saveNewRecordInTable() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:json/test-case.json");
        String jsonStr = fromStreamToString(resource.getInputStream());
        LOGGER.info("Test-Case: \n{}", jsonStr);

        Person testUnsavedPerson = objectMapper.readValue(jsonStr, Person.class);
        personRepo.save(testUnsavedPerson);

        List<Person> personList = personRepo.findAll();
        assertNotNull(personList);
        assertEquals(2, personList.size());
    }
}