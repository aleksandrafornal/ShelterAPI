package com.example.shelterapi.controller.response;

import java.util.UUID;

public class FulfilPetResponse {
    private final UUID petId;

    public FulfilPetResponse(UUID petId) {
        this.petId = petId;
    }

    public UUID getPetId() {
        return petId;
    }

    @Override
    public String toString() {
        return "FulfilPetResponse{" +
                "petId=" + petId +
                '}';
    }
}
