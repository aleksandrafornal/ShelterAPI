package com.example.shelterapi.controller.request;

public class FulfilPetRequest {
    private String name;
    private String species;
    private int age;
    private String size;

    public FulfilPetRequest(){}

    public FulfilPetRequest(String name, String species, int age, String size) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.size = size;
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

    @Override
    public String toString() {
        return "FulfilPetRequest{" +
                "name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", age=" + age +
                ", size='" + size + '\'' +
                '}';
    }
}
