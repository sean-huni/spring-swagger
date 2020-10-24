package io.swagger.persistence.entity;


import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class Person extends AbstractEntityClass {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Long id;                    // (string): Unique identifier of the organization
    private String aristocraticTitle;   // (string, optional): e.g. Baron, Graf, Earl,… ,
    private String familyName;          // (string, optional): Contains the non-chosen or inherited name. Also known as last name in the Western context ,
    private String fullName;            // (string, optional): Full name flatten (first, middle, and last names) ,
    private String gender;              // (string, optional): Gender ,
    private LocalDate birthDate;        // (string, optional): Birth date ,
    private String countryOfBirth;      // (string, optional): Country where the individual was born ,
    private LocalDate deathDate;        // (string, optional): Date of death ,

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /*
    Spring-Data-REST Generated fields:
        @baseType (string, optional): When sub-classing, this defines the super-class ,
        @schemaLocation (string, optional): A URI to a JSON-Schema file that defines additional attributes and relationships ,
        @type (string, optional): When sub-classing, this defines the sub-class entity name ,
        href (string, optional): Hyperlink to access the organization ,
     */
}
