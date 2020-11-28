package io.swagger.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenderTest {

    @Test
    void values() {
        assertEquals(2, Gender.values().length);
    }

    @Test
    void givenGenderEnum_whenRetrievingValueOfStringFemale_andValueStringOfMale_thenReturnGender_andStringValues() {
        Character femaleChar = 'F';
        Gender gFemale = Gender.FEMALE;
        Character maleChar = 'M';
        Gender gMale = Gender.MALE;

        assertEquals(femaleChar, gFemale.getGender().charValue());
        assertEquals("FEMALE", gFemale.toString());
        assertEquals(maleChar, gMale.getGender().charValue());
        assertEquals("MALE", gMale.toString());
    }

    @Test
    void givenGenderEnum_whenRetrievingValueOfStringFemale_andValueStringOfFemaleGenderEnum() {
        assertEquals('F', Gender.FEMALE.getGender());
        assertEquals("FEMALE", Gender.FEMALE.toString());
    }

    @Test
    void givenGenderEnum_whenRetrievingValueOfStringMale_andValueStringOfMaleGenderEnum() {
        assertEquals('M', Gender.MALE.getGender());
        assertEquals("MALE", Gender.MALE.toString());
    }
}