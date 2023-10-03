package com.example.shelterapi.data;

import com.example.shelterapi.logic.model.Adopter;
import com.example.shelterapi.logic.repository.AdopterRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
public class InMemoryAdopterRepository implements AdopterRepository {

    private final HashMap<UUID, Adopter> adopterList = new HashMap<>();

    @Override
    public void save(Adopter adopter) {
        adopterList.put(adopter.id(), adopter);
    }

    @Override
    public Adopter getById(UUID id) {
        return adopterList.get(id);
    }

    @Override
    public List<Adopter> getAll() {
        return adopterList.values().stream().toList();
    }
}
