package com.example.shelterapi;

import com.example.shelterapi.logic.model.Adopter;
import com.example.shelterapi.logic.model.Requirements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.example.shelterapi.logic.AdoptionProspectValidator.checkFulfilledRequirements;
import static com.example.shelterapi.logic.model.Requirements.*;

class AdoptionProspectValidatorTest {

    @Test
    void shouldReturnMissingRequirements() {
        // Given
        List<Requirements> requirements = List.of(
                HOUSE_WITH_GARDEN,
                IS_TRAINED,
                GOOD_SALARY,
                NETTED_BALCONY
        );

        Adopter adopter = new Adopter(
                UUID.randomUUID(),
                "Name",
                "Surname",
                25,
                List.of(
                        GOOD_SALARY,
                        NETTED_BALCONY
                )
        );

        // When
        List<Requirements> result = checkFulfilledRequirements(
                adopter,
                requirements
        );

        // Then
        Assertions.assertEquals(
                result,
                List.of(HOUSE_WITH_GARDEN, IS_TRAINED)
        );
    }

}
