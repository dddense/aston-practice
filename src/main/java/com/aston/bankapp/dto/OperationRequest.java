package com.aston.bankapp.dto;

import com.aston.bankapp.enumeration.OperationType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonTypeInfo(property = "type", use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TransferOperationRequest.class, name = "TRANSFER"),
        @JsonSubTypes.Type(value = DepositOperationRequest.class, name = "DEPOSIT"),
        @JsonSubTypes.Type(value = WithdrawOperationRequest.class, name = "WITHDRAW")
})
public abstract class OperationRequest {

    @NonNull
    protected OperationType type;

    @Min(100)
    protected long amount;

    @NotBlank
    @Size(min = 4, max = 4)
    protected String pin;
}
