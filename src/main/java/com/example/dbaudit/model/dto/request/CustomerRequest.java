package com.example.dbaudit.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
    Long id,
    @NotBlank
    String name,
    @NotBlank
    String email,
    String company
) {
}
