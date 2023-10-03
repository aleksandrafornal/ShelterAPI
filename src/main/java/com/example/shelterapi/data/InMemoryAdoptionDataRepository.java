package com.example.shelterapi.data;

import com.example.shelterapi.logic.model.AdoptionData;
import com.example.shelterapi.logic.repository.AdoptionDataRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
public class InMemoryAdoptionDataRepository implements AdoptionDataRepository {
    private final HashMap<UUID, AdoptionData> adoptionDataList = new HashMap<>();

    public HashMap<UUID, AdoptionData> getAdoptionDataList() {
        return adoptionDataList;
    }

    @Override
    public String toString() {
        return "InMemoryAdoptionDataRepository{" +
                "adoptionDataList=" + adoptionDataList +
                '}';
    }

    @Override
    public void save(AdoptionData adoptionData) {
        adoptionDataList.put(adoptionData.adoptionId(), adoptionData);
    }

    @Override
    public AdoptionData getById(UUID id) {
        return adoptionDataList.get(id);
    }

    @Override
    public List<AdoptionData> getAll() {
        return adoptionDataList.values().stream().toList();
    }
}
