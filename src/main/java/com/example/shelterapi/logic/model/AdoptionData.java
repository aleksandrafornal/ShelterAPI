package com.example.shelterapi.logic.model;

import java.time.Instant;
import java.util.UUID;

public record AdoptionData(
        Adopter adopter,
        Pet pet,
        Instant dateOfAdoption,
        UUID adoptionId
) {}
