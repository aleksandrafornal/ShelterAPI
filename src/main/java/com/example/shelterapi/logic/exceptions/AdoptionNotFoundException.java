package com.example.shelterapi.logic.exceptions;

import java.util.UUID;

public class AdoptionNotFoundException extends RuntimeException{
    private final UUID id;

    public AdoptionNotFoundException(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AdoptionNotFoundException{" +
                "id=" + id +
                '}';
    }
}
