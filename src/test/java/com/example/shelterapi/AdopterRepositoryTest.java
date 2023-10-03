package com.example.shelterapi;

import com.example.shelterapi.data.InDatabaseAdopterRepository;
import com.example.shelterapi.logic.model.Adopter;
import com.example.shelterapi.logic.model.Requirements;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class AdopterRepositoryTest {


    @Autowired
    InDatabaseAdopterRepository inDatabaseAdopterRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Test
    public void shouldAddAdopterToRepository() {
        //Given
        AdopterTestRepository adopterTestRepository = new AdopterTestRepository(jdbcTemplate);
        Adopter adopter = new Adopter(
                UUID.randomUUID(), "Name", "Surname", 24, new ArrayList<>() {{
            add(Requirements.HOUSE_WITH_GARDEN);
            add(Requirements.GOOD_SALARY);
            add(Requirements.NETTED_BALCONY);
        }}
        );

        //When
        inDatabaseAdopterRepository.save(adopter);


        //Then
        Adopter result = adopterTestRepository.findInDbById(adopter.id());
        Assertions.assertEquals(adopter, result);


    }

    @Test
    public void shouldGetAdopterFromRepositoryById() throws SQLException {
        //Given
        Adopter adopter = new Adopter(
                UUID.randomUUID(), "Name", "Surname", 24, new ArrayList<>() {{
            add(Requirements.HOUSE_WITH_GARDEN);
            add(Requirements.GOOD_SALARY);
            add(Requirements.NETTED_BALCONY);
        }}
        );

        inDatabaseAdopterRepository.save(adopter);

        //When
        Adopter result = inDatabaseAdopterRepository.getById(adopter.id());

        //Then
        Assertions.assertEquals(adopter, result);

    }

    @Test
    public void shouldReturnAllAdoptersFromRepository() throws SQLException {
        //Given
       Adopter adopter = new Adopter(
                UUID.randomUUID(), "Name", "Surname", 24, new ArrayList<>() {{
            add(Requirements.HOUSE_WITH_GARDEN);
            add(Requirements.GOOD_SALARY);
            add(Requirements.NETTED_BALCONY);
        }}
        );
        Adopter adopter2 = new Adopter(
                UUID.randomUUID(), "Name", "Surname", 23, new ArrayList<>() {{
            add(Requirements.HOUSE_WITH_GARDEN);
            add(Requirements.GOOD_SALARY);
            add(Requirements.NETTED_BALCONY);
        }}
        );

        inDatabaseAdopterRepository.save(adopter);
        inDatabaseAdopterRepository.save(adopter2);

        //When
        List<Adopter> result = inDatabaseAdopterRepository.getAll();

        //Then
        Assertions.assertTrue(result.contains(adopter) && result.contains(adopter2));

    }

    @AfterEach
    public void shouldClearTable() throws SQLException {
        String[] tablesToClear = {"adopters", "adopters_requirements"};
        String clearTable = "TRUNCATE TABLE ";
        for (String table : tablesToClear) {
            jdbcTemplate.update(clearTable + table);
        }

    }

}
