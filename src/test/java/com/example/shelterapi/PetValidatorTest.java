package com.example.shelterapi;

import com.example.shelterapi.controller.request.FulfilPetRequest;
import com.example.shelterapi.logic.PetValidator;
import com.example.shelterapi.logic.model.ValidationError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PetValidatorTest {
    @Test
    public void shouldDoNothingIfEverythingIsCorrect() {
        //Given
        FulfilPetRequest petRequest = new FulfilPetRequest(
                "Name",
                "dog",
                3,
                "small"
        );

        //When
        List<ValidationError> errorsList = PetValidator.validate(petRequest);

        //Then
        Assertions.assertTrue(errorsList.isEmpty());
    }

    @Test
    public void shouldAddToListIfLengthOfNameIsWrong(){
        //Given
        FulfilPetRequest petRequest = new FulfilPetRequest(
                "N",
                "dog",
                3,
                "small"
        );

        //When
        List<ValidationError> errorsList = PetValidator.validate(petRequest);

        //Then
        assertEquals(1, errorsList.size());
    }

    @Test
    public void shouldAddToListIfNameDoesNotContainOnlyLetters(){
        //Given
        FulfilPetRequest petRequest = new FulfilPetRequest(
                "N@me",
                "dog",
                3,
                "small"
        );

        //When
        List<ValidationError> errorsList = PetValidator.validate(petRequest);

        //Then
        assertEquals(1, errorsList.size());
    }

    @Test
    public void shouldAddToListIfSpeciesDoesNotContainOnlyLetters(){
        //Given
        FulfilPetRequest petRequest = new FulfilPetRequest(
                "Name",
                "d0g",
                3,
                "small"
        );

        //When
        List<ValidationError> errorsList = PetValidator.validate(petRequest);

        //Then
        assertEquals(1, errorsList.size());
    }

    @Test
    public void shouldAddToListIfSizeIsWrong(){
        //Given
        FulfilPetRequest petRequest = new FulfilPetRequest(
                "Name",
                "dog",
                3,
                "size"
        );

        //When
        List<ValidationError> errorsList = PetValidator.validate(petRequest);

        //Then
        assertEquals(1, errorsList.size());
    }


    @Test
    public void gathersAllErrors(){
        //Given
        FulfilPetRequest petRequest = new FulfilPetRequest(
                null,
                null,
                -3,
                null
        );

        //When
        List<ValidationError> errorsList = PetValidator.validate(petRequest);

        //Then
        assertEquals(4, errorsList.size());
    }
}