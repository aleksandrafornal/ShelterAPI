package com.example.shelterapi;

import com.example.shelterapi.logic.model.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdoptionDataTestRepository {
    private final JdbcTemplate jdbcTemplate;

    public AdoptionDataTestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AdoptionData findInDbbyId(UUID uuid) {
        String SELECT_ADOPTION_DATA =
                "SELECT adoption_data.id, adoption_data.adopters_id, adoption_data.pet_id, " +
                        "adoption_data.adoption_date, adopters.adopters_name, adopters.adopters_surname, " +
                        "adopters.adopters_age, pets.name, pets.species, pets.age, pets.size, pets.status " +
                        "FROM adoption_data " +
                        "JOIN adopters ON adoption_data.adopters_id = adopters.id " +
                        "JOIN pets ON adoption_data.pet_id = pets.id " +
                        "WHERE adoption_data.id = ?";

        String SELECT_REQUIREMENTS_BY_ADOPTER_ID =
                "SELECT * FROM adopters_requirements WHERE adopters_id = ?";


        return jdbcTemplate.queryForObject(
                SELECT_ADOPTION_DATA,
                (ResultSet rs, int rowNum) -> new AdoptionData(
                        new Adopter(
                                UUID.fromString(rs.getString("adoption_data.adopters_id")),
                                rs.getString("adopters.adopters_name"),
                                rs.getString("adopters.adopters_surname"),
                                rs.getInt("adopters.adopters_age"),
                                jdbcTemplate.queryForObject(
                                        SELECT_REQUIREMENTS_BY_ADOPTER_ID,
                                        (ResultSet rs1, int rowNum1) -> {
                                            List<Requirements> requirements = new ArrayList<>();
                                            requirements.add(Requirements.valueOf(rs1.getString("requirement")));
                                            while (rs1.next()) {
                                                requirements.add(Requirements.valueOf(rs1.getString("requirement")));
                                            }
                                            return requirements;
                                        },
                                        rs.getString("adoption_data.adopters_id")
                                )
                        ),
                        new Pet(
                                rs.getString("pets.name"),
                                rs.getString("pets.species"),
                                rs.getInt("pets.age"),
                                rs.getString("pets.size"),
                                UUID.fromString(rs.getString("adoption_data.pet_id")),
                                StatusOfPet.valueOf(rs.getString("pets.status"))
                        ),
                        rs.getTimestamp("adoption_data.adoption_date").toInstant(),
                        UUID.fromString(rs.getString("adoption_data.id"))
                ),
                uuid.toString()
        );
    }
}
