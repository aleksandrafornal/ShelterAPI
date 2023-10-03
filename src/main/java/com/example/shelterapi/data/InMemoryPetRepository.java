package com.example.shelterapi.data;

import com.example.shelterapi.logic.model.Pet;
import com.example.shelterapi.logic.model.StatusOfPet;
import com.example.shelterapi.logic.repository.PetRepository;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class InMemoryPetRepository implements PetRepository {
    private final HashMap<UUID, Pet> petList = new HashMap<>();

    public HashMap<UUID, Pet> getPetList() {
        return petList;
    }

    public void adopt(Pet pet){
        pet.setStatus(StatusOfPet.NOT_AVAILABLE);
    }

    @Override
    public void save(Pet pet) {
        pet.setStatus(StatusOfPet.AVAILABLE);
        petList.put(pet.getPetId(), pet);
    }

    @Override
    public Pet getById(UUID uuid) {return petList.get(uuid);}

    @Override
    public List<Pet> getAll() {return petList.values().stream().toList();}
}
