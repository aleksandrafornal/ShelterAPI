package com.example.shelterapi.logic;

import com.example.shelterapi.controller.request.FulfilPetRequest;
import com.example.shelterapi.logic.model.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PetValidator {
    public static List<ValidationError> validate(FulfilPetRequest petRequest) {
        List<ValidationError> errors = new ArrayList<>();
        if (petRequest.getName() == null){
            check(
                    petRequest.getName(),
                    name -> !(name == null),
                    "name",
                    "Name can't be null",
                    errors
            );
        }else {
            check(
                    petRequest.getName(),
                    name -> name.length() >= 2 && name.length() <= 10,
                    "name",
                    "Name >=2 && <=10",
                    errors
            );

            check(
                    petRequest.getName(),
                    name -> name.matches("[a-zA-Z]+"),
                    "name",
                    "Name contains only letters",
                    errors
            );
        }

        if (petRequest.getSpecies() == null){
            check(
                    petRequest.getSpecies(),
                    species -> !(species == null),
                    "species",
                    "Species can't be null",
                    errors
            );
        } else {
            check(
                    petRequest.getSpecies(),
                    species -> species.matches("[a-zA-Z]+"),
                    "species",
                    "Species contains only letters",
                    errors
            );
        }
        if (petRequest.getSize() == null){
            check(
                    petRequest.getSize(),
                    size -> !(size == null),
                    "size",
                    "Size can't be null",
                    errors
            );
        }else {
            check(
                    petRequest.getSize(),
                    size -> new ArrayList<String>(){{
                        add("small");
                        add("medium");
                        add("large");
                    }}.contains(size),
                    "size",
                    "Size: small, medium or large",
                    errors

            );
        }
        check(
                petRequest.getAge(),
                age -> age >= 0,
                "age",
                "Age>=0",
                errors
        );
        return errors;

    }

    private static <T> void check(
            T value,
            Predicate<T> rule,
            String fieldName,
            String message,
            List<ValidationError> errors
    ) {
        if (!rule.test(value)) errors.add(
                new ValidationError(
                        fieldName,
                        message
                )
        );
    }
}
