package io.swagger.config.init;

import io.swagger.SecurityUtility;
import io.swagger.commons.Gender;
import io.swagger.config.InitData;
import io.swagger.config.security.IVaultConfig;
import io.swagger.exception.unchecked.SystemInitException;
import io.swagger.persistence.entity.Person;
import io.swagger.persistence.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@Component
@Profile("live")
public class InitDataLive implements InitData {
    private final IVaultConfig iVaultConfig;
    private PersonRepo personRepo;

    public InitDataLive(IVaultConfig iVaultConfig) {
        this.iVaultConfig = iVaultConfig;
    }

    @Autowired
    public void setPersonRepo(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @PostConstruct
    void check1stRecord() {
        String s = "Service record was not found in Database";
        SecurityUtility.createSecurityContext(iVaultConfig.getServiceUsername(), iVaultConfig.getServicePassword(), "ROLE_SERVICE");
        personRepo.save(createRecord());
        Person p = personRepo.findAllByFullNameContainingIgnoreCase("lic").stream().findFirst()
                .orElseThrow(() -> new SystemInitException(s)); //should not happen at all. throw exception.
        SecurityContextHolder.clearContext();

        if (Objects.isNull(p)) {
            throw new SystemInitException(s);
        }
    }

    private Person createRecord() {
        Person p = new Person();
        p.setAristocraticTitle("Elf");
        p.setFullName("Alice");
        p.setFamilyName("Erling");
        p.setGender(Gender.Female);
        p.setBirthDate(ZonedDateTime.of(2001, 1, 1, 17, 25, 0, 0, ZoneId.systemDefault()));
        p.setCountryOfBirth("South Africa");
        p.setVersion("1.0.0");
        return p;
    }

    public void init() {
    }
}
