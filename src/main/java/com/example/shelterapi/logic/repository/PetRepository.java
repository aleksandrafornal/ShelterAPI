package com.example.shelterapi.logic.repository;

import com.example.shelterapi.logic.model.Adopter;
import com.example.shelterapi.logic.model.Pet;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface PetRepository {
    void save(Pet pet);
    Pet getById(UUID uuid);
    List<Pet> getAll();
    void adopt(Pet pet);
}
