package com.example.shelterapi.controller.request;

import java.util.ArrayList;
import java.util.List;

public class AdopterRequest {
    private String name;
    private String surname;
    private int age;
    private List<String> fulfilledRequirements;

    public AdopterRequest(){}

    public AdopterRequest(String name, String surname, int age, List<String> fulfilledRequirements) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.fulfilledRequirements = fulfilledRequirements;
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public List<String> getFulfilledRequirements() {
        return fulfilledRequirements;
    }

    @Override
    public String toString() {
        return "AdopterRequest{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", fulfilledRequirements=" + fulfilledRequirements +
                '}';
    }
}
