package com.aston.bankapp.dto;

import java.util.UUID;

public record AccountResponse(
        UUID id,
        String beneficiaryName,
        String number,
        long balance
) {
}
