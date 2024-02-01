package com.aston.bankapp.service;

import com.aston.bankapp.dto.DepositOperationRequest;
import com.aston.bankapp.dto.TransferOperationRequest;
import com.aston.bankapp.dto.WithdrawOperationRequest;
import com.aston.bankapp.entity.Operation;

import java.util.List;

public interface OperationService {

    void deposit(DepositOperationRequest depositOperationRequest);

    void withdraw(WithdrawOperationRequest withdrawOperationRequest);

    void transfer(TransferOperationRequest transferOperationRequest);

    List<Operation> getOperationsByAccountNumber(String accountNumber);
}
