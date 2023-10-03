package com.example.shelterapi.logic.model;

import com.example.shelterapi.data.InMemoryPetRepository;
import com.example.shelterapi.logic.repository.PetRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.shelterapi.logic.AdoptionProspectValidator.checkFulfilledRequirements;

public class Shelter {

    private final String city;
    private final int capacity;
    private final PetRepository petRepository;

    private static final List<Requirements> REQUIREMENTS = List.of(
            Requirements.IS_TRAINED,
            Requirements.HOUSE_WITH_GARDEN,
            Requirements.GOOD_SALARY,
            Requirements.NETTED_BALCONY
    );


    public Shelter(String city, int capacity, PetRepository petRepository) {
        this.city = city;
        this.capacity = capacity;
        this.petRepository = petRepository;
    }

    public Map<UUID, Pet> getPetRepository() {
        return petRepository.getAll().stream().collect(Collectors.toMap(
                Pet::getPetId, pet -> pet
        ));
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "city='" + city + '\'' +
                ", capacity=" + capacity +
                ", petList=" + petRepository +
                '}';
    }

    public void admitPet(Pet pet) {
        if (petRepository.getAll().size() + 1 <= capacity) {
            petRepository.save(pet);
        } else {
            System.out.println("You have to take this pet somewhere else");
        }
    }

    public boolean hasNoSpace() {
        return petRepository.getAll().size() == capacity;
    }

    public void trainAdopter(Adopter adopter) {
        adopter.fulfilledRequirements().add(Requirements.IS_TRAINED);
    }

    public void petAdoption(Pet pet, Adopter adopter) {

        List<Requirements> missingRequirements = checkFulfilledRequirements(adopter, REQUIREMENTS);

        if (missingRequirements.size() == 1 && missingRequirements.contains(Requirements.IS_TRAINED)) {
            trainAdopter(adopter);
            System.out.printf("Congratulations! You can take %s home!%n", pet.getName());
            petRepository.adopt(pet);
        } else if (missingRequirements.isEmpty()) {
            System.out.printf("Congratulations! You can take %s home!%n", pet.getName());
            petRepository.adopt(pet);
        } else if (missingRequirements.size() > 1) {
            System.out.println(
                    "Sorry, you can't adopt in our shelter, you have to change that, so that you can: " + missingRequirements
            );
        }
    }

    public Pet findPet(UUID id) {
        return petRepository.getById(id);
    }

    public List<Pet> findAllPets() {
        return petRepository.getAll();
    }

}
