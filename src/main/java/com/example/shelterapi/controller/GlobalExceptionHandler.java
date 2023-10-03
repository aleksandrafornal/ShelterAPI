package com.example.shelterapi.controller;

import com.example.shelterapi.logic.exceptions.PetNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity badRequest() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(PetNotFoundException.class)
    public String petNotFound(UUID id){
        return "Pet of this id = " + id + " does not exists.";
    }
}
