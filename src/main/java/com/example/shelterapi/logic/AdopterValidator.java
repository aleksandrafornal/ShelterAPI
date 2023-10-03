package com.example.shelterapi.logic;

import com.example.shelterapi.controller.request.AdopterRequest;
import com.example.shelterapi.logic.model.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class AdopterValidator {

    public static List<ValidationError> validate(AdopterRequest adopterRequest) {
        List<ValidationError> errors = new ArrayList<>();
        if (adopterRequest.getName() == null) {
            check(
                    adopterRequest.getName(),
                    name -> !(name == null),
                    "name",
                    "Name can't be null",
                    errors
            );
        } else {
            check(
                    adopterRequest.getName(),
                    name -> name.length() <= 12 && name.length() >= 3,
                    "name",
                    "Name >=3 && <= 12",
                    errors
            );


            check(
                    adopterRequest.getName(),
                    name -> name.matches("[a-zA-Z]+"),
                    "name",
                    "Name contains only letters",
                    errors
            );
        }

        if (adopterRequest.getSurname() == null){
            check(
                    adopterRequest.getSurname(),
                    surname -> !(surname == null),
                    "surname",
                    "Surname can't be null",
                    errors
            );
        } else {
            check(
                    adopterRequest.getSurname(),
                    surname -> surname.length() <= 12 && surname.length() >= 3,
                    "surname",
                    "Surname >=3 && <= 12",
                    errors
            );


            check(
                    adopterRequest.getSurname(),
                    surname -> surname.matches("[a-zA-Z]+"),
                    "surname",
                    "Surname contains only letters",
                    errors
            );
        }

        check(
                adopterRequest.getAge(),
                age -> age >= 18,
                "age",
                "Age >= 18",
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
