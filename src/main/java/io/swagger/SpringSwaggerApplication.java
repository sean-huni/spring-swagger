package io.swagger;

import io.swagger.config.InitData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories
@Import(SpringDataRestConfiguration.class)
public class SpringSwaggerApplication {
    private InitData initData;

    @Autowired
    public void setInitData(InitData initData) {
        this.initData = initData;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSwaggerApplication.class, args);
    }

    @PostConstruct
    void runInit() {
        initData.init();
    }
}
