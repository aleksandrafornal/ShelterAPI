package com.example.shelterapi.logic;


import com.example.shelterapi.logic.model.Adopter;
import com.example.shelterapi.logic.model.Requirements;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class AdoptionProspectValidator {
    public static List<Requirements> checkFulfilledRequirements(
            Adopter adopter,
            List<Requirements> requirements
    ) {
        return CollectionUtils.selectRejected(
                requirements,
                (item) -> adopter.fulfilledRequirements().contains(item)
        ).stream().toList();
    }
}
