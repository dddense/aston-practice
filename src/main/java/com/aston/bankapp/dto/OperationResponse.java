package com.aston.bankapp.dto;

import com.aston.bankapp.enumeration.OperationType;

import java.time.LocalDateTime;
import java.util.UUID;

public record OperationResponse(
        UUID id,
        long amount,
        LocalDateTime createdAt,
        OperationType type,

        String target
) {
}
