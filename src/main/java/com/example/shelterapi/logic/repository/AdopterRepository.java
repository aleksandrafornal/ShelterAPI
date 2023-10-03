package com.example.shelterapi.logic.repository;

import com.example.shelterapi.logic.model.Adopter;

import java.util.List;
import java.util.UUID;

public interface AdopterRepository {
    void save(Adopter adopter);
    Adopter getById(UUID uuid);
    List<Adopter> getAll();
}
