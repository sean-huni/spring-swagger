package io.swagger.commons;

public enum Gender {
    MALE('M'),
    FEMALE('F');

    private final Character category;

    Gender(Character category) {
        this.category = category;
    }

    public Character getCategory() {
        return category;
    }
}
