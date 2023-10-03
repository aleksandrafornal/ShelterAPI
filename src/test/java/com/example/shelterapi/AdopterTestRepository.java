package com.example.shelterapi;

import com.example.shelterapi.logic.model.Adopter;
import com.example.shelterapi.logic.model.Requirements;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdopterTestRepository {
    private final JdbcTemplate jdbcTemplate;

    public AdopterTestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Adopter findInDbById(UUID id){
        String SELECT_ADOPTER_BY_ID =
                "SELECT * FROM adopters WHERE id = ?";

        String SELECT_REQUIREMENTS_BY_ADOPTER_ID =
                "SELECT * FROM adopters_requirements WHERE adopters_id = ?";
        return jdbcTemplate.queryForObject(
                SELECT_ADOPTER_BY_ID,
                (ResultSet rs, int rowNum) -> new Adopter(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("adopters_name"),
                        rs.getString("adopters_surname"),
                        rs.getInt("adopters_age"),
                        jdbcTemplate.queryForObject(
                                SELECT_REQUIREMENTS_BY_ADOPTER_ID,
                                (ResultSet rs1, int rowNum1) -> {
                                    List<Requirements> requirements = new ArrayList<>();
                                    requirements.add(Requirements.valueOf(rs1.getString("requirement")));
                                    while (rs1.next()){
                                        requirements.add(Requirements.valueOf(rs1.getString("requirement")));
                                    }
                                    return requirements;
                                },
                                id.toString()
                        )
                ),
                id.toString()
        );

    }
}
