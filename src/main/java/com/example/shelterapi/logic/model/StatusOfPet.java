package com.example.shelterapi.logic.model;

public enum StatusOfPet {
    AVAILABLE("AVAILABLE"),
    NOT_AVAILABLE("NOT_AVAILABLE");
    private String string;

    StatusOfPet(String string) {
        this.string = string;
    }
    @Override public String toString() {
        return string;
    }

}
