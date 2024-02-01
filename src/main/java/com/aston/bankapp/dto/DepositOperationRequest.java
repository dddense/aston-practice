package com.aston.bankapp.dto;

import com.aston.bankapp.enumeration.OperationType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DepositOperationRequest extends OperationRequest {

    {
        type = OperationType.DEPOSIT;
    }

    @NotBlank
    private String accountNumber;
}
