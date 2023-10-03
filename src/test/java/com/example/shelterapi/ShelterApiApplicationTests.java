package com.example.shelterapi;

import com.example.shelterapi.logic.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class ShelterApiApplicationTests {
    @Autowired
    Shelter shelter;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void shouldClearTable() throws SQLException {
        String clearTable = "TRUNCATE TABLE pets";
        jdbcTemplate.execute(clearTable);
    }

    @Test
    void shouldAddPetToShelter(){
        //Given
        Pet pet = new Pet(
                "Name",
                "dog", 2,
                "small",
                UUID.randomUUID(),
                StatusOfPet.AVAILABLE
        );

        //When
        shelter.admitPet(pet);

        //Then
        Assertions.assertTrue(shelter.getPetRepository().containsKey(pet.getPetId()));
    }
    @Test
    void shouldGetPetById(){
        //Given
        Pet pet = new Pet(
                "Name",
                "dog", 2,
                "small",
                UUID.randomUUID(),
                StatusOfPet.AVAILABLE
        );
        shelter.admitPet(pet);

        //When
        Pet result = shelter.findPet(pet.getPetId());
        //Then
        Assertions.assertEquals(pet, result);
    }

    @Test
    void shouldGetAllPets(){
        //Given
        Pet pet1 = new Pet(
                "Name",
                "dog", 2,
                "small",
                UUID.randomUUID(),
                StatusOfPet.AVAILABLE
        );

        Pet pet2 = new Pet(
                "Name",
                "cat", 2,
                "small",
                UUID.randomUUID(),
                StatusOfPet.AVAILABLE
        );

        List<Pet> petList = new ArrayList<>(){{
            add(pet1);
            add(pet2);
        }};

        shelter.admitPet(pet1);
        shelter.admitPet(pet2);

        //When
        List<Pet> result = shelter.findAllPets();

        //Then
        Assertions.assertTrue(petList.containsAll(result) && result.containsAll(petList));
    }

    @Test
    void returnNullIfPetDoesNotExist(){
        //When
        Pet pet = shelter.findPet(UUID.randomUUID());
        //Then
        Assertions.assertNull(pet);
    }



}
