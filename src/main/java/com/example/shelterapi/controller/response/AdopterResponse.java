package com.example.shelterapi.controller.response;

import java.util.UUID;

public class AdopterResponse {
    private final UUID adopterId;

    public AdopterResponse(UUID adopterId) {
        this.adopterId = adopterId;
    }

    public UUID getAdopterId() {
        return adopterId;
    }

    @Override
    public String toString() {
        return "AdopterResponse{" +
                "adopterId=" + adopterId +
                '}';
    }
}
