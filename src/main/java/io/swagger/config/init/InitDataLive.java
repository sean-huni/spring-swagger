package io.swagger.config.init;

import io.swagger.commons.Gender;
import io.swagger.config.InitData;
import io.swagger.persistence.entity.Person;
import io.swagger.persistence.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Component
@Profile("live")
public class InitDataLive implements InitData {
    private PersonRepo personRepo;

    @Autowired
    public void setPersonRepo(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @PostConstruct
    void check1stRecord() throws Exception {
        personRepo.save(createRecord());
        Optional<Person> optionalPerson = personRepo.findAllByFullNameContainingIgnoreCase("ea").stream().findFirst();
        optionalPerson.orElseThrow(Exception::new); //should not happen at all. throw exception.
    }

    private Person createRecord() {
        Person p = new Person();
        p.setAristocraticTitle("Elf");
        p.setFullName("Sean");
        p.setFamilyName("Huni");
        p.setGender(Gender.Male);
        p.setBirthDate(ZonedDateTime.of(1989, 8, 24, 17, 25, 0, 0, ZoneId.systemDefault()));
        p.setCountryOfBirth("Zimbabwe");
        p.setVersion("1.0.0");
        return p;
    }

    public void init() {
    }
}
