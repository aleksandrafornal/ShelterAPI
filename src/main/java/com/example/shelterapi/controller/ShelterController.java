package com.example.shelterapi.controller;

import com.example.shelterapi.controller.request.AdopterRequest;
import com.example.shelterapi.controller.request.FulfilPetRequest;
import com.example.shelterapi.controller.response.AdopterResponse;
import com.example.shelterapi.controller.response.FulfilPetResponse;
import com.example.shelterapi.logic.*;
import com.example.shelterapi.logic.model.*;
import com.example.shelterapi.logic.repository.AdopterRepository;
import com.example.shelterapi.logic.repository.AdoptionDataRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ShelterController {
    private final Shelter shelter;
    private final AdopterRepository inDatabaseAdopterRepository;
    private final AdoptionDataRepository inDatabaseAdoptionDataRepository;

    public ShelterController(Shelter shelter, AdopterRepository inMemoryAdopterRepository, AdoptionDataRepository inMemoryAdoptionDataRepository) {
        this.shelter = shelter;
        this.inDatabaseAdopterRepository = inMemoryAdopterRepository;
        this.inDatabaseAdoptionDataRepository = inMemoryAdoptionDataRepository;
    }


    @PostMapping("/pets")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity fulfillPet(
            @RequestBody FulfilPetRequest petRequest
    ) {
        List<ValidationError> validatorList = PetValidator.validate(petRequest);
        if (validatorList.isEmpty()) {
            if (shelter.hasNoSpace()) {
                return ResponseEntity
                        .status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .build();
            }
            Pet pet = new Pet(
                    petRequest.getName(),
                    petRequest.getSpecies(),
                    petRequest.getAge(),
                    petRequest.getSize(),
                    UUID.randomUUID(),
                    null
            );
            shelter.admitPet(pet);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new FulfilPetResponse(pet.getPetId()));
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(validatorList);
    }

    @PostMapping("/adopters")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addAdopter(
            @RequestBody AdopterRequest adopterRequest
    ) {
        List<ValidationError> validatorList = AdopterValidator.validate(adopterRequest);
        if (validatorList.isEmpty()) {
            Adopter adopter = new Adopter(
                    UUID.randomUUID(),
                    adopterRequest.getName(),
                    adopterRequest.getSurname(),
                    adopterRequest.getAge(),
                    adopterRequest.getFulfilledRequirements().stream().map(Requirements::valueOf).collect(Collectors.toList())
            );
            inDatabaseAdopterRepository.save(adopter);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new AdopterResponse(adopter.id()));

        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(validatorList);

    }

    @GetMapping("/adopters/{id}")
    public ResponseEntity getAdopter(@PathVariable String id) {

        UUID newId = UUID.fromString(id);
        Adopter adopter = inDatabaseAdopterRepository.getById(newId);

        if (adopter == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adopter);
    }


    @GetMapping("/adopters")
    public List<Adopter> getAllAdopters() {
        return inDatabaseAdopterRepository.getAll();
    }


    @PutMapping("/pets/{petId}/adoption/{adopterId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity adoptPet(
            @PathVariable UUID petId, @PathVariable UUID adopterId
    ) {

        Adopter adopter = inDatabaseAdopterRepository.getById(adopterId);
        Pet pet = shelter.findPet(petId);

        if (!(shelter.getPetRepository().containsKey(petId)) || (adopter == null)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        shelter.petAdoption(pet, adopter);
        AdoptionData adoptionData = new AdoptionData(
                adopter,
                pet,
                Instant.now(),
                UUID.randomUUID()
        );
        inDatabaseAdoptionDataRepository.save(adoptionData);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(adoptionData);
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity getPet(@PathVariable String id) {

        UUID newId = UUID.fromString(id);

        if (!(shelter.getPetRepository().containsKey(newId))) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(shelter.findPet(newId));
    }

    @GetMapping("/pets")
    public List<Pet> getAllPets() {
        return shelter.findAllPets();
    }

    @GetMapping("/adoption-data/{id}")
    public ResponseEntity getAdoptionData(@PathVariable String id) {

        UUID newId = UUID.fromString(id);

        AdoptionData adoptionData = inDatabaseAdoptionDataRepository.getById(newId);

        if (adoptionData == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adoptionData);
    }

    @GetMapping("/adoption-data")
    public List<AdoptionData> getAllAdoptionData() {
        return inDatabaseAdoptionDataRepository.getAll();
    }



    @PatchMapping("/adopters/{id}")
    public ResponseEntity trainAdopter(
            @PathVariable String id
    ) {
        UUID newId = UUID.fromString(id);
        Adopter adopter = inDatabaseAdopterRepository.getById(newId);

        if (adopter == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        shelter.trainAdopter(adopter);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
