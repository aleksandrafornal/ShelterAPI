package com.example.shelterapi.data;

import com.example.shelterapi.logic.model.Adopter;
import com.example.shelterapi.logic.model.Requirements;
import com.example.shelterapi.logic.repository.AdopterRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class InDatabaseAdopterRepository implements AdopterRepository {
    private final JdbcTemplate jdbcTemplate;

    public InDatabaseAdopterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final static String ADD_ADOPTER =
            "INSERT INTO adopters (id, adopters_name, adopters_surname, adopters_age) VALUES (?, ?, ?, ?)";

    private final static String ADD_REQUIREMENTS_TO_ADOPTER =
            "INSERT INTO adopters_requirements (adopters_id, requirement) VALUES (?, ?)";

    private final static String SELECT_ADOPTER_BY_ID =
            "SELECT * FROM adopters WHERE id = ?";

    private final static String SELECT_REQUIREMENTS_BY_ADOPTER_ID =
            "SELECT * FROM adopters_requirements WHERE adopters_id = ?";

    private final static String SELECT_ADOPTERS =
            "SELECT * FROM adopters";

    @Override
    public void save(Adopter adopter) {

        jdbcTemplate.update(
                ADD_ADOPTER,
                adopter.id().toString(), adopter.name(), adopter.surname(), adopter.age()
        );
        List<Requirements> fulfilledRequirements = adopter.fulfilledRequirements();
        for (Requirements requirement : fulfilledRequirements) {
            jdbcTemplate.update(ADD_REQUIREMENTS_TO_ADOPTER, adopter.id().toString(), String.valueOf(requirement));

        }
    }

    @Override
    public Adopter getById(UUID uuid) {

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
                                uuid.toString()
                        )
                ),
                uuid.toString()
        );
    }

    @Override
    public List<Adopter> getAll() {

        return jdbcTemplate.query(
                SELECT_ADOPTERS,
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
                                rs.getString("id")
                        )
                )
        );
    }
}
