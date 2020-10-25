package io.swagger.persistence.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Entity
@Table(schema = "party_identity", name = "person")
public class Person extends AbstractEntityClass {

    @Length(min = 1, max = 50)
    private String aristocraticTitle;   // (string, optional): e.g. Baron, Graf, Earl,… ,
    private String familyName;          // (string, optional): Contains the non-chosen or inherited name. Also known as last name in the Western context ,
    private String fullName;            // (string, optional): Full name flatten (first, middle, and last names) ,
    private String gender;              // (string, optional): Gender ,
    private LocalDate birthDate;        // (string, optional): Birth date ,
    private String countryOfBirth;      // (string, optional): Country where the individual was born ,
    private LocalDate deathDate;        // (string, optional): Date of death ,

    public Person() {
        super();
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }
}
