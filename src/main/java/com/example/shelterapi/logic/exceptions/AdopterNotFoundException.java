package com.example.shelterapi.logic.exceptions;

import java.util.UUID;

public class AdopterNotFoundException extends RuntimeException{
    private final UUID id;

    public AdopterNotFoundException(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AdopterNotFoundException{" +
                "id=" + id +
                '}';
    }
}
