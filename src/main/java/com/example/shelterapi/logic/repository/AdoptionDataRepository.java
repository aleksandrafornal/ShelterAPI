package com.example.shelterapi.logic.repository;

import com.example.shelterapi.logic.model.AdoptionData;

import java.util.List;
import java.util.UUID;

public interface AdoptionDataRepository {

    void save(AdoptionData adoptionData);
    AdoptionData getById(UUID uuid);
    List<AdoptionData> getAll();
}
