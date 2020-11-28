package io.swagger.config.init;

import io.swagger.SecurityUtility;
import io.swagger.commons.Gender;
import io.swagger.config.InitData;
import io.swagger.persistence.entity.Person;
import io.swagger.persistence.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@Profile("dev")
public class InitDataTest implements InitData {
    private PersonRepo personRepo;

    @Autowired
    public void setPersonRepo(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @PostConstruct
    public void check1stRecord() {
        SecurityUtility.createSecurityContext("spring", "spring_password", "ROLE_SERVICE", "ROLE_ADMIN");
        personRepo.save(createRecord());
        SecurityContextHolder.clearContext();
    }

    private Person createRecord() {
        Person p = new Person();
        p.setAristocraticTitle("Elf");
        p.setFullName("Sean");
        p.setFamilyName("Huni");
        p.setGender(Gender.MALE);
        p.setBirthDate(ZonedDateTime.of(1989, 8, 24, 17, 25, 0, 0, ZoneId.systemDefault()));
        p.setCountryOfBirth("Zimbabwe");
        p.setVersion("1.0.0");
        return p;
    }

    public void init() {
        //Used to inject the instance of the Test configuration.
    }
}
