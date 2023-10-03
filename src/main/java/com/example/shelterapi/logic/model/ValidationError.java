package com.example.shelterapi.logic.model;

public record ValidationError(
        String fieldName,
        String rule
) {}
