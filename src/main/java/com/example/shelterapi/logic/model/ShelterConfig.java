package com.example.shelterapi.logic.model;

import com.example.shelterapi.data.InDatabasePetRepository;
import com.example.shelterapi.logic.repository.PetRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShelterConfig {
    private final PetRepository inDatabasePetRepository;

    public ShelterConfig(InDatabasePetRepository inDatabasePetRepository) {
        this.inDatabasePetRepository = inDatabasePetRepository;
    }

    @Bean
    public Shelter getShelter() {
        return new Shelter("Poznan", 500, inDatabasePetRepository);
    }
}
