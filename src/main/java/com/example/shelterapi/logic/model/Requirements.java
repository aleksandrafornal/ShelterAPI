package com.example.shelterapi.logic.model;

public enum Requirements {
    HOUSE_WITH_GARDEN("HOUSE_WITH_GARDEN"),
    GOOD_SALARY("GOOD_SALARY"),
    IS_TRAINED("IS_TRAINED"),
    NETTED_BALCONY("NETTED_BALCONY");

    private String str;

    Requirements(String str) {
        this.str = str;
    }

    @Override public String toString() {
        return str;
    }
}
