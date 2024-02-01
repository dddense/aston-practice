package com.aston.bankapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountCreationRequest(
        @NotBlank
        @Size(min = 4, max = 4)
        String pin,

        @NotBlank
        String phoneNumber
) {
}
