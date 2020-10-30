package io.swagger.persistence.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.commons.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@ApiModel(description = "Individual-Person represents a single human being (a man, woman or child). The individual can be a customer, an employee or any other person that the organization needs to store information about.")
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Entity
@Table(schema = "party_identity", name = "person")
public class Person extends AbstractEntityClass {

    @ApiModelProperty(value = "e.g. Baron, Graf, Earl,…")
    @Length(min = 1, max = 50)
    private String aristocraticTitle;   // (string, optional): e.g. Baron, Graf, Earl,… ,
    @NotNull
    @ApiModelProperty(value = "Contains the non-chosen or inherited name. Also known as last name in the Western context")
    private String familyName;          // (string, optional): Contains the non-chosen or inherited name. Also known as last name in the Western context ,
    @ApiModelProperty(value = "Full name flatten (first, middle, and last names)")
    @NotNull
    @Length(min = 1, max = 255)
    private String fullName;            // (string, optional): Full name flatten (first, middle, and last names) ,
    @ApiModelProperty(value = "Gender")
    @NotNull
    private Gender gender;              // (string, optional): Gender ,
    @ApiModelProperty(value = "Birth date")
    private ZonedDateTime birthDate;        // (string, optional): Birth date ,
    @ApiModelProperty(value = "Country where the individual was born")
    private String countryOfBirth;      // (string, optional): Country where the individual was born ,
    @ApiModelProperty(value = "Date of death")
    private ZonedDateTime deathDate;        // (string, optional): Date of death ,

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
