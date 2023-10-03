package com.example.shelterapi.data;

import java.time.Instant;
import java.util.UUID;

public class AdoptionDatabaseInfo {
    private final UUID id;
    private final UUID adopters_id;
    private final UUID pet_id;
    private final Instant dateOfAdoption;

    public AdoptionDatabaseInfo(UUID id, UUID adopters_id, UUID pet_id, Instant dateOfAdoption) {
        this.id = id;
        this.adopters_id = adopters_id;
        this.pet_id = pet_id;
        this.dateOfAdoption = dateOfAdoption;
    }

    public UUID getId() {
        return id;
    }

    public UUID getAdopters_id() {
        return adopters_id;
    }

    public UUID getPet_id() {
        return pet_id;
    }

    public Instant getDateOfAdoption() {
        return dateOfAdoption;
    }

    @Override
    public String toString() {
        return "AdoptionDatabaseInfo{" +
                "id=" + id +
                ", adopters_id=" + adopters_id +
                ", pet_id=" + pet_id +
                ", dateOfAdoption=" + dateOfAdoption +
                '}';
    }
}
