package com.example.shelterapi.logic.model;

import java.util.Objects;
import java.util.UUID;

public class Pet {
    private final String name;
    private final String species;
    private final int age;
    private final String size;
    private final UUID petId;
    private StatusOfPet status;

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", age=" + age +
                ", size='" + size + '\'' +
                ", petId=" + petId +
                ", status=" + status +
                '}';
    }

    public Pet(String name, String species, int age, String size, UUID petId, StatusOfPet status) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.size = size;
        this.petId = petId;
        this.status = status;
    }

    public void setStatus(StatusOfPet status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public int getAge() {
        return age;
    }

    public String getSize() {
        return size;
    }

    public UUID getPetId() {
        return petId;
    }

    public StatusOfPet getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pet pet = (Pet) o;

        if (age != pet.age) return false;
        if (!Objects.equals(name, pet.name)) return false;
        if (!Objects.equals(species, pet.species)) return false;
        if (!Objects.equals(size, pet.size)) return false;
        if (!Objects.equals(petId, pet.petId)) return false;
        return status == pet.status;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (species != null ? species.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (petId != null ? petId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
