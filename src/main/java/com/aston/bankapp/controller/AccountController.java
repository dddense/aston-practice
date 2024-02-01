package com.aston.bankapp.controller;

import com.aston.bankapp.dto.AccountCreationRequest;
import com.aston.bankapp.dto.AccountResponse;
import com.aston.bankapp.exception.ExceptionDto;
import com.aston.bankapp.mapper.AccountMapper;
import com.aston.bankapp.service.AccountService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Account controller")
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    @Operation(summary = "Create account")
    @PostMapping
    public AccountResponse createAccount(@RequestBody @Valid AccountCreationRequest accountCreationRequest) {
        return accountMapper.toDto(accountService.createAccount(accountCreationRequest));
    }

    @Operation(summary = "Get user accounts")
    @GetMapping
    public List<AccountResponse> getAccounts(@RequestParam String phoneNumber) {
        return accountMapper.toDto(accountService.getAccounts(phoneNumber));
    }

    @Operation(
            summary = "Get account info",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found",
                            content = @Content(schema = @Schema(implementation = ExceptionDto.class))
                    )
            }
    )
    @GetMapping("/{accountNumber}")
    public AccountResponse getAccount(@PathVariable("accountNumber") String accountNumber) {
        return accountMapper.toDto(accountService.getByNumber(accountNumber));
    }

}
