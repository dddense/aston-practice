package com.aston.bankapp.dto;

import com.aston.bankapp.enumeration.OperationType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class WithdrawOperationRequest extends OperationRequest {

    {
        type = OperationType.WITHDRAW;
    }

    @NotBlank
    private String accountNumber;
}
