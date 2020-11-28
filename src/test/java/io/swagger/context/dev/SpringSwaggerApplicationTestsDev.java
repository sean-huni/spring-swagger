package io.swagger.context.dev;

import io.swagger.SpringSwaggerApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(classes = SpringSwaggerApplication.class)
class SpringSwaggerApplicationTestsDev {

    @Test
    @DisplayName("Load Spring Context Dev-Profile Successfully")
    void contextLoads() {
        assert true;
    }
}
