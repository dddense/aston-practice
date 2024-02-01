package com.aston.bankapp.service;

import com.aston.bankapp.dto.AccountCreationRequest;
import com.aston.bankapp.dto.DepositOperationRequest;
import com.aston.bankapp.dto.TransferOperationRequest;
import com.aston.bankapp.dto.WithdrawOperationRequest;
import com.aston.bankapp.entity.Account;

import java.util.List;

public interface AccountService {

    Account createAccount(AccountCreationRequest accountCreationRequest);

    List<Account> getAccounts(String phoneNumber);

    Account getByNumber(String number);

    void deposit(Account account, String pin, long amount);

    void withdraw(Account account, String pin, long amount);

    void transfer(Account source, Account target, String pin, long amount);

    void validatePin(String pin, Account account);
}
