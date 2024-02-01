package com.aston.bankapp.dto;

import com.aston.bankapp.enumeration.OperationType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TransferOperationRequest extends OperationRequest {
    {
        type = OperationType.TRANSFER;
    }

    @NotBlank
    private String sourceAccountNumber;

    @NotBlank
    private String targetAccountNumber;
}
