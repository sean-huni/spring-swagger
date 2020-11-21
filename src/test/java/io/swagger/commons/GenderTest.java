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
        Gender gFemale = Gender.Female;
        Character maleChar = 'M';
        Gender gMale = Gender.Male;

        assertEquals(femaleChar, gFemale.getGender().charValue());
        assertEquals("Female", gFemale.toString());
        assertEquals(maleChar, gMale.getGender().charValue());
        assertEquals("Male", gMale.toString());
    }

    @Test
    void givenGenderEnum_whenRetrievingValueOfStringFemale_andValueStringOfFemaleGenderEnum() {
        assertEquals('F', Gender.Female.getGender());
        assertEquals("Female", Gender.Female.toString());
    }

    @Test
    void givenGenderEnum_whenRetrievingValueOfStringMale_andValueStringOfMaleGenderEnum() {
        assertEquals('M', Gender.Male.getGender());
        assertEquals("Male", Gender.Male.toString());
    }
}