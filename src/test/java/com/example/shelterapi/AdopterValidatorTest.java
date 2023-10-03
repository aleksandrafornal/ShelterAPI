package com.example.shelterapi;

import com.example.shelterapi.controller.request.AdopterRequest;
import com.example.shelterapi.data.InMemoryPetRepository;
import com.example.shelterapi.logic.AdopterValidator;
import com.example.shelterapi.logic.model.Requirements;
import com.example.shelterapi.logic.model.Shelter;
import com.example.shelterapi.logic.model.ValidationError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdopterValidatorTest {
    private final Shelter shelter = new Shelter("Test-City", 1000, new InMemoryPetRepository());

    @Test
    public void shouldDoNothingIfEverythingIsCorrect() {
        //Given
        AdopterRequest adopterRequest = new AdopterRequest(
                "Name",
                "Surname",
                23,
                new ArrayList<>() {{
                    add(String.valueOf(Requirements.GOOD_SALARY));
                    add(String.valueOf(Requirements.NETTED_BALCONY));
                    add(String.valueOf(Requirements.HOUSE_WITH_GARDEN));
                    add(String.valueOf(Requirements.IS_TRAINED));
                }}
                );

        //When
        List<ValidationError> errorsList = AdopterValidator.validate(adopterRequest);

        //Then
        Assertions.assertTrue(errorsList.isEmpty());
    }

    @Test
    public void shouldAddToTheListIfLengthOfNameIsWrong(){
        //Given
        AdopterRequest adopterRequest = new AdopterRequest(
                "Na",
                "Surname",
                23,
                new ArrayList<>() {{
                    add(String.valueOf(Requirements.GOOD_SALARY));
                    add(String.valueOf(Requirements.NETTED_BALCONY));
                    add(String.valueOf(Requirements.HOUSE_WITH_GARDEN));
                    add(String.valueOf(Requirements.IS_TRAINED));
                }}
        );

        //When
        List<ValidationError> errorsList = AdopterValidator.validate(adopterRequest);

        //Then
        assertEquals(1, errorsList.size());
    }

    @Test
    public void shouldAddToTheListIfNameContainsNotOnlyLetters(){
        //Given
        AdopterRequest adopterRequest = new AdopterRequest(
                "N@me",
                "Surname",
                23,
                new ArrayList<>() {{
                    add(String.valueOf(Requirements.GOOD_SALARY));
                    add(String.valueOf(Requirements.NETTED_BALCONY));
                    add(String.valueOf(Requirements.HOUSE_WITH_GARDEN));
                    add(String.valueOf(Requirements.IS_TRAINED));
                }}
        );

        //When
        List<ValidationError> errorsList = AdopterValidator.validate(adopterRequest);

        //Then
        assertEquals(1, errorsList.size());
    }



    @Test
    public void shouldAddToTheListIfLengthOfSurnameIsWrong(){
        //Given
        AdopterRequest adopterRequest = new AdopterRequest(
                "Name",
                "S",
                23,
                new ArrayList<>() {{
                    add(String.valueOf(Requirements.GOOD_SALARY));
                    add(String.valueOf(Requirements.NETTED_BALCONY));
                    add(String.valueOf(Requirements.HOUSE_WITH_GARDEN));
                    add(String.valueOf(Requirements.IS_TRAINED));
                }}
        );

        //When
        List<ValidationError> errorsList = AdopterValidator.validate(adopterRequest);

        //Then
        assertEquals(1, errorsList.size());
    }

    @Test
    public void shouldAddToTheListIfSurnameContainsNotOnlyLetters(){
        //Given
        AdopterRequest adopterRequest = new AdopterRequest(
                "Name",
                "Surn@m3",
                23,
                new ArrayList<>() {{
                    add(String.valueOf(Requirements.GOOD_SALARY));
                    add(String.valueOf(Requirements.NETTED_BALCONY));
                    add(String.valueOf(Requirements.HOUSE_WITH_GARDEN));
                    add(String.valueOf(Requirements.IS_TRAINED));
                }}
        );

        //When
        List<ValidationError> errorsList = AdopterValidator.validate(adopterRequest);

        //Then
        assertEquals(1, errorsList.size());
    }


    @Test
    public void shouldAddToTheListIfAgeIsWrong(){
        //Given
        AdopterRequest adopterRequest = new AdopterRequest(
                "Name",
                "Surname",
                13,
                new ArrayList<>() {{
                    add(String.valueOf(Requirements.GOOD_SALARY));
                    add(String.valueOf(Requirements.NETTED_BALCONY));
                    add(String.valueOf(Requirements.HOUSE_WITH_GARDEN));
                    add(String.valueOf(Requirements.IS_TRAINED));
                }}
        );

        //When
        List<ValidationError> errorsList = AdopterValidator.validate(adopterRequest);

        //Then
        assertEquals(1, errorsList.size());
    }

    @Test
    public void shouldAddToTheListIfNameAndSurnameIsNull(){
        //Given
        AdopterRequest adopterRequest = new AdopterRequest(
                null,
                null,
                23,
                new ArrayList<>() {{
                    add(String.valueOf(Requirements.GOOD_SALARY));
                    add(String.valueOf(Requirements.NETTED_BALCONY));
                    add(String.valueOf(Requirements.HOUSE_WITH_GARDEN));
                    add(String.valueOf(Requirements.IS_TRAINED));
                }}
        );

        //When
        List<ValidationError> errorsList = AdopterValidator.validate(adopterRequest);

        //Then
        assertEquals(2, errorsList.size());
    }


}