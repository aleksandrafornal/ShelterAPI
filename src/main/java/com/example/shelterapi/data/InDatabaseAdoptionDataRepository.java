package com.example.shelterapi.data;

import com.example.shelterapi.logic.model.*;
import com.example.shelterapi.logic.repository.AdoptionDataRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class InDatabaseAdoptionDataRepository implements AdoptionDataRepository {
    private final JdbcTemplate jdbcTemplate;
    private final static String ADD_ADOPTER =
            "INSERT INTO adopters (id, adopters_name, adopters_surname, adopters_age) VALUES (?, ?, ?, ?)";
    private final static String ADD_REQUIREMENTS_TO_ADOPTER =
            "INSERT INTO adopters_requirements (adopters_id, requirement) VALUES (?, ?)";
    private final static String ADD_PET =
            "INSERT INTO pets (id, name, species, age, size, status) VALUES (?, ?, ?, ?, ?, ?)";
    private final static String ADD_ADOPTION =
            "INSERT INTO adoption_data (id, adopters_id, pet_id, adoption_date) VALUES (?, ?, ?, ?)";
    private final static String SELECT_REQUIREMENTS_BY_ADOPTER_ID =
            "SELECT * FROM adopters_requirements WHERE adopters_id = ?";
    private final static String SELECT_ADOPTION_DATA =
            "SELECT adoption_data.id, adoption_data.adopters_id, adoption_data.pet_id, " +
                    "adoption_data.adoption_date, adopters.adopters_name, adopters.adopters_surname, " +
                    "adopters.adopters_age, pets.name, pets.species, pets.age, pets.size, pets.status " +
                    "FROM adoption_data " +
                    "JOIN adopters ON adoption_data.adopters_id = adopters.id " +
                    "JOIN pets ON adoption_data.pet_id = pets.id " +
                    "WHERE adoption_data.id = ?";

    private final static String SELECT_ALL_ADOPTION_DATA =
            "SELECT adoption_data.id, adoption_data.adopters_id, adoption_data.pet_id, " +
                    "adoption_data.adoption_date, adopters.adopters_name, adopters.adopters_surname, " +
                    "adopters.adopters_age, adopters_requirements.requirement, pets.name, " +
                    "pets.species, pets.age, pets.size, pets.status " +
                    "FROM adoption_data " +
                    "JOIN adopters ON adoption_data.adopters_id = adopters.id " +
                    "JOIN adopters_requirements ON adoption_data.adopters_id = adopters_requirements.adopters_id " +
                    "JOIN pets ON adoption_data.pet_id = pets.id";

    public InDatabaseAdoptionDataRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(AdoptionData adoptionData) {

        jdbcTemplate.update(
                ADD_ADOPTION,
                adoptionData.adoptionId().toString(),
                adoptionData.adopter().id().toString(),
                adoptionData.pet().getPetId().toString(),
                Timestamp.from(
                        adoptionData.dateOfAdoption()
                )
        );

        jdbcTemplate.update(
                ADD_ADOPTER,
                adoptionData.adopter().id().toString(),
                adoptionData.adopter().name(),
                adoptionData.adopter().surname(),
                adoptionData.adopter().age()
        );

        List<Requirements> fulfilledRequirements = adoptionData.adopter().fulfilledRequirements();
        for (Requirements requirement : fulfilledRequirements) {
            jdbcTemplate.update(
                    ADD_REQUIREMENTS_TO_ADOPTER, adoptionData.adopter().id().toString(), String.valueOf(requirement)
            );

        }

        jdbcTemplate.update(
                ADD_PET,
                adoptionData.pet().getPetId().toString(),
                adoptionData.pet().getName(),
                adoptionData.pet().getSpecies(),
                adoptionData.pet().getAge(),
                adoptionData.pet().getSize(),
                StatusOfPet.NOT_AVAILABLE.toString()
        );

    }

    @Override
    public AdoptionData getById(UUID uuid) {

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

    @Override
    public List<AdoptionData> getAll() {
        return jdbcTemplate.query(
                SELECT_ALL_ADOPTION_DATA,
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
                )
        );
    }
}
