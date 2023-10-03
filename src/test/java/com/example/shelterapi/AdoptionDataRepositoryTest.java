package com.example.shelterapi;

import com.example.shelterapi.data.InDatabaseAdoptionDataRepository;
import com.example.shelterapi.logic.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class AdoptionDataRepositoryTest {
    @Autowired
    InDatabaseAdoptionDataRepository inDatabaseAdoptionDataRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void shouldAddAdoptionDataToRepository() {
        //Given
        AdoptionDataTestRepository adoptionDataTestRepository =
                new AdoptionDataTestRepository(jdbcTemplate);
        Adopter adopter = new Adopter(
                UUID.randomUUID(), "Name", "Surname", 25, new ArrayList<>() {{
            add(Requirements.HOUSE_WITH_GARDEN);
            add(Requirements.GOOD_SALARY);
            add(Requirements.NETTED_BALCONY);
        }}
        );

        Pet pet = new Pet(
                "Name", "cat", 6, "small", UUID.randomUUID(), StatusOfPet.NOT_AVAILABLE
        );
        AdoptionData adoptionData = new AdoptionData(
                adopter, pet, Instant.parse("2023-07-24T12:34:56Z"), UUID.randomUUID()
        );
        //When
        inDatabaseAdoptionDataRepository.save(adoptionData);

        //Then
        AdoptionData result = adoptionDataTestRepository.findInDbbyId(adoptionData.adoptionId());
        Assertions.assertEquals(adoptionData, result);
    }


    @Test
    public void shouldGetAdoptionDataById(){
        //Given
        Adopter adopter = new Adopter(
                UUID.randomUUID(), "Name", "Surname", 25, new ArrayList<>() {{
            add(Requirements.HOUSE_WITH_GARDEN);
            add(Requirements.GOOD_SALARY);
            add(Requirements.NETTED_BALCONY);
        }}
        );
        Pet pet = new Pet(
                "Name", "cat", 6, "small", UUID.randomUUID(), StatusOfPet.NOT_AVAILABLE
        );
        AdoptionData adoptionData = new AdoptionData(
                adopter, pet, Instant.parse("2023-07-24T12:34:56Z"), UUID.randomUUID()
        );
        inDatabaseAdoptionDataRepository.save(adoptionData);

        //When
        AdoptionData result = inDatabaseAdoptionDataRepository.getById(adoptionData.adoptionId());

        //Then
        Assertions.assertEquals(adoptionData, result);
    }

    @Test
    public void shouldGetAllAdoptionData(){
        //Given
        Adopter adopter1 = new Adopter(
                UUID.randomUUID(), "Name", "Surname", 25, new ArrayList<>() {{
            add(Requirements.HOUSE_WITH_GARDEN);
            add(Requirements.GOOD_SALARY);
            add(Requirements.NETTED_BALCONY);
        }}
        );

        Adopter adopter2 = new Adopter(
                UUID.randomUUID(), "Name", "Surname", 28, new ArrayList<>() {{
            add(Requirements.HOUSE_WITH_GARDEN);
            add(Requirements.GOOD_SALARY);
            add(Requirements.NETTED_BALCONY);
        }}
        );

        Pet pet1 = new Pet(
                "Name", "cat", 6, "small", UUID.randomUUID(), StatusOfPet.NOT_AVAILABLE
        );

        Pet pet2 = new Pet(
                "Name", "dog", 3, "large", UUID.randomUUID(), StatusOfPet.NOT_AVAILABLE
        );

        AdoptionData adoptionData1 = new AdoptionData(
                adopter1, pet1, Instant.parse("2023-07-24T12:34:56Z"), UUID.randomUUID()
        );

        AdoptionData adoptionData2 = new AdoptionData(
                adopter2, pet2, Instant.parse("2023-07-24T12:34:56Z"), UUID.randomUUID()
        );

        inDatabaseAdoptionDataRepository.save(adoptionData1);
        inDatabaseAdoptionDataRepository.save(adoptionData2);

        //When
        List<AdoptionData> result = inDatabaseAdoptionDataRepository.getAll();

        //Then
        Assertions.assertTrue(result.contains(adoptionData1) && result.contains(adoptionData2));
    }

    @AfterEach
    public void shouldClearTable() throws SQLException {
        String[] tablesToClear = {"adopters", "adopters_requirements", "adoption_data", "pets"};
        String clearTable = "TRUNCATE TABLE ";
        for (String table : tablesToClear) {
            jdbcTemplate.update(clearTable + table);
        }

    }


}
