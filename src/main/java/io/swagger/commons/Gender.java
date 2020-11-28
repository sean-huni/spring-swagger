package io.swagger.commons;

public enum Gender {
    MALE('M'),
    FEMALE('F');

    private final Character gender;

    Gender(Character gender) {
        this.gender = gender;
    }

    public Character getGender() {
        return gender;
    }
}
