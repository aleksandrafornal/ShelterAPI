package com.example.shelterapi.logic.exceptions;

import java.util.UUID;

public class PetNotFoundException extends RuntimeException{
    private final UUID uuid;

    public PetNotFoundException(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "PetNotFoundException{" +
                "uuid=" + uuid +
                '}';
    }
}
