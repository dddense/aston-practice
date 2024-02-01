package com.aston.bankapp.controller;

import com.aston.bankapp.dto.DepositOperationRequest;
import com.aston.bankapp.dto.OperationResponse;
import com.aston.bankapp.dto.TransferOperationRequest;
import com.aston.bankapp.dto.WithdrawOperationRequest;
import com.aston.bankapp.exception.ExceptionDto;
import com.aston.bankapp.mapper.OperationMapper;
import com.aston.bankapp.service.OperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Operation controller")
@RestController
@RequestMapping("/api/v1/operations")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    private final OperationMapper operationMapper;

    @Operation(summary = "Get account' operations")
    @GetMapping("/accounts/{accountNumber}")
    public List<OperationResponse> getAccountOperations(@PathVariable("accountNumber") String accountNumber) {
        return operationMapper.toDto(operationService.getOperationsByAccountNumber(accountNumber));
    }

    @Operation(
            summary = "Transfer money to another account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful transfer"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request body, check pin or amount",
                            content = @Content(schema = @Schema(implementation = ExceptionDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Can't perform transfer, need to check balance",
                            content = @Content(schema = @Schema(implementation = ExceptionDto.class))
                    )
            }
    )
    @PostMapping("/transfer")
    public void transfer(@RequestBody @Valid TransferOperationRequest transferOperationRequest) {
        operationService.transfer(transferOperationRequest);
    }

    @Operation(
            summary = "Withdraw money from account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful withdraw"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request body, check pin or amount",
                            content = @Content(schema = @Schema(implementation = ExceptionDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Can't perform withdraw, need to check balance",
                            content = @Content(schema = @Schema(implementation = ExceptionDto.class))
                    )
            }
    )
    @PostMapping("/withdraw")
    public void withdraw(@RequestBody @Valid WithdrawOperationRequest withdrawOperationRequest) {
        operationService.withdraw(withdrawOperationRequest);
    }

    @Operation(
            summary = "Deposit money to account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful deposit"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request body, check pin or amount",
                            content = @Content(schema = @Schema(implementation = ExceptionDto.class))
                    )
            }
    )
    @PostMapping("/deposit")
    public void deposit(@RequestBody @Valid DepositOperationRequest depositOperationRequest) {
        operationService.deposit(depositOperationRequest);
    }
}
