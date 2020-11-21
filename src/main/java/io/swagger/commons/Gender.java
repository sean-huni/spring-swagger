package io.swagger.commons;

public enum Gender {
    Male('M'),
    Female('F');

    private final Character gender;

    Gender(Character gender) {
        this.gender = gender;
    }

    public Character getGender() {
        return gender;
    }
}
