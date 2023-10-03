package com.example.shelterapi.data;

import com.example.shelterapi.logic.model.Pet;
import com.example.shelterapi.logic.model.StatusOfPet;
import com.example.shelterapi.logic.repository.PetRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

@Component
public class InDatabasePetRepository implements PetRepository {
    private final JdbcTemplate jdbcTemplate;

    public InDatabasePetRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final static String ADD_PET =
            "INSERT INTO pets (id, name, species, age, size, status) VALUES (?, ?, ?, ?, ?, ?)";
    private final static String SELECT_PET_BY_ID =
            "SELECT * FROM pets WHERE id = ?";
    private final static String SELECT_PETS =
            "SELECT * FROM pets";

    private final static String CHANGE_STATUS_OF_PET =
            "UPDATE pets SET status = ? where id = ?";
    @Override
    public void save(Pet pet) {
        jdbcTemplate.update(
                ADD_PET,
                pet.getPetId().toString(),
                pet.getName(),
                pet.getSpecies(),
                pet.getAge(),
                pet.getSize(),
                StatusOfPet.AVAILABLE.toString()
        );
    }

    @Override
    public Pet getById(UUID uuid) {
        try {
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
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Pet> getAll() {
        return jdbcTemplate.query(
                SELECT_PETS,
                (ResultSet rs, int rowNum) -> new Pet(
                        rs.getString("name"),
                        rs.getString("species"),
                        rs.getInt("age"),
                        rs.getString("size"),
                        UUID.fromString(rs.getString("id")),
                        StatusOfPet.valueOf(rs.getString("status"))
                )
        );
    }

    @Override
    public void adopt(Pet pet) {
        jdbcTemplate.update(
                CHANGE_STATUS_OF_PET,
                StatusOfPet.NOT_AVAILABLE.toString(),
                pet.getPetId().toString()
        );
    }
}
