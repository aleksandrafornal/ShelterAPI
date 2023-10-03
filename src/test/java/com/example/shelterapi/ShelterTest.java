package com.example.shelterapi;

import com.example.shelterapi.data.InMemoryPetRepository;
import com.example.shelterapi.logic.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

class ShelterTest {

    private final Shelter shelter = new Shelter("Test-City", 1000, new InMemoryPetRepository());

    @Test
    void shouldTrainAdopter() {
        // Given
        Adopter adopter = new Adopter(
                UUID.randomUUID(),
                "Name",
                "Surname",
                25,
                new ArrayList<>() {{
                    add(Requirements.GOOD_SALARY);
                    add(Requirements.NETTED_BALCONY);
                    add(Requirements.HOUSE_WITH_GARDEN);
                }}

        );

        // When
        shelter.trainAdopter(adopter);

        // Then
        Assertions.assertTrue(
                adopter.fulfilledRequirements().contains(Requirements.IS_TRAINED)
        );
    }

    @Test
    void adopterCanAdoptPetWhenHaveAllRequirements() {
        // Given
        Adopter adopter = new Adopter(
                UUID.randomUUID(),
                "Name",
                "Surname",
                25,
                new ArrayList<>() {{
                    add(Requirements.GOOD_SALARY);
                    add(Requirements.NETTED_BALCONY);
                    add(Requirements.HOUSE_WITH_GARDEN);
                    add(Requirements.IS_TRAINED);
                }}
        );
        Pet pet = new Pet(
                "Name",
                "dog",
                2,
                "small",
                UUID.randomUUID(),
                StatusOfPet.AVAILABLE
        );
        shelter.admitPet(pet);

        // When
        shelter.petAdoption(pet, adopter);

        // Then
        Assertions.assertEquals(
                shelter.getPetRepository().get(pet.getPetId()).getStatus(), StatusOfPet.NOT_AVAILABLE
        );
    }

    @Test
    void adopterCanAdoptWhenOnlyIsTrainedRequirementIsMissing() {
        // Given
        Adopter adopter = new Adopter(
                UUID.randomUUID(),
                "Name",
                "Surname",
                25,
                new ArrayList<>() {{
                    add(Requirements.GOOD_SALARY);
                    add(Requirements.NETTED_BALCONY);
                    add(Requirements.HOUSE_WITH_GARDEN);
                }}
        );
        Pet pet = new Pet(
                "Name",
                "Dog",
                2,
                "small",
                UUID.randomUUID(),
                StatusOfPet.AVAILABLE
        );
        shelter.admitPet(pet);

        // When
        shelter.petAdoption(pet, adopter);

        // Then
        Assertions.assertEquals(
                shelter.getPetRepository().get(pet.getPetId()).getStatus(), StatusOfPet.NOT_AVAILABLE
        );

    }


    @Test
    void adopterIsNotAbleToAdoptPetWhenDoesNotHasNecessaryRequirements() {
        // Given
        Adopter adopter = new Adopter(
                UUID.randomUUID(),
                "Name",
                "Surname",
                25,
                new ArrayList<>() {{
                    add(Requirements.GOOD_SALARY);
                    add(Requirements.NETTED_BALCONY);
                }}
        );
        Pet pet = new Pet(
                "Name",
                "Dog",
                2,
                "small",
                UUID.randomUUID(),
                StatusOfPet.AVAILABLE
        );
        shelter.admitPet(pet);

        // When
        shelter.petAdoption(pet, adopter);

        // Then
        Assertions.assertEquals(
                shelter.getPetRepository().get(pet.getPetId()).getStatus(), StatusOfPet.AVAILABLE
        );
    }

}