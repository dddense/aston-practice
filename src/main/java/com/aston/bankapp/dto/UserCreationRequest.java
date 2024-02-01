package com.aston.bankapp.dto;

import jakarta.validation.constraints.NotBlank;

public record UserCreationRequest(
        @NotBlank
        String phoneNumber,
        @NotBlank
        String name
) {
}
