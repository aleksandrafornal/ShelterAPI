package com.example.shelterapi.logic.model;

import java.util.List;
import java.util.UUID;

public record Adopter(
        UUID id,
        String name,
        String surname,
        int age,
        List<Requirements> fulfilledRequirements
) {
}
