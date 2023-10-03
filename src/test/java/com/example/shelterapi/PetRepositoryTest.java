package com.example.shelterapi;

import com.example.shelterapi.data.InDatabasePetRepository;
import com.example.shelterapi.logic.model.Pet;
import com.example.shelterapi.logic.model.StatusOfPet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class PetRepositoryTest {
    @Autowired
    private InDatabasePetRepository inDatabasePetRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void shouldAddPetToRepository() {
        //Given
        PetTestRepository petTestRepository = new PetTestRepository(jdbcTemplate);

        Pet pet = new Pet(
                "Name", "cat", 6, "small", UUID.randomUUID(), StatusOfPet.AVAILABLE
        );

        //When
        inDatabasePetRepository.save(pet);

        //Then
        Pet result = petTestRepository.findInDbById(pet.getPetId());
        Assertions.assertEquals(pet, result);

    }

    @Test
    public void shouldGetPetFromRepositoryById() {
        //Given
        Pet pet = new Pet(
                "Name", "cat", 6, "small", UUID.randomUUID(), StatusOfPet.AVAILABLE
        );
        inDatabasePetRepository.save(pet);

        //When
        Pet result = inDatabasePetRepository.getById(pet.getPetId());

        //Then
        Assertions.assertEquals(pet, result);
    }

    @Test
    public void shouldReturnAllPetsFromRepository() {
        //Given
        Pet pet1 = new Pet(
                "Name", "cat", 6, "small", UUID.randomUUID(), StatusOfPet.AVAILABLE
        );
        Pet pet2 = new Pet(
                "Name", "cat", 1, "small", UUID.randomUUID(), StatusOfPet.AVAILABLE
        );
        inDatabasePetRepository.save(pet1);
        inDatabasePetRepository.save(pet2);

        //When
        List<Pet> result = inDatabasePetRepository.getAll();

        //Then
        Assertions.assertTrue(result.contains(pet1) && result.contains(pet2));
    }

    @AfterEach
    public void shouldClearTable() throws SQLException {
        String clearTable = "TRUNCATE TABLE pets";
        jdbcTemplate.update(clearTable);
    }
}
