package com.example.shelterapi;

import com.example.shelterapi.logic.model.Pet;
import com.example.shelterapi.logic.model.StatusOfPet;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.util.UUID;

public class PetTestRepository {
    private final JdbcTemplate jdbcTemplate;

    public PetTestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Pet findInDbById(UUID uuid){
        String SELECT_PET_BY_ID =
                "SELECT * FROM pets WHERE id = ?";

        return jdbcTemplate.queryForObject(
                SELECT_PET_BY_ID,
                (ResultSet rs, int rowNum) -> new Pet(
                        rs.getString("name"),
                        rs.getString("species"),
                        rs.getInt("age"),
                        rs.getString("size"),
                        UUID.fromString(rs.getString("id")),
                        StatusOfPet.valueOf(rs.getString("status"))
                ),
                uuid.toString()
        );
    }
}
