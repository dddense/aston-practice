package com.aston.bankapp.service;

import com.aston.bankapp.dto.DepositOperationRequest;
import com.aston.bankapp.dto.TransferOperationRequest;
import com.aston.bankapp.dto.WithdrawOperationRequest;
import com.aston.bankapp.entity.Account;
import com.aston.bankapp.entity.Operation;
import com.aston.bankapp.enumeration.OperationType;
import com.aston.bankapp.mapper.OperationMapper;
import com.aston.bankapp.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;

    private final AccountService accountService;

    private final OperationMapper operationMapper;

    @Override
    @Transactional
    public void deposit(DepositOperationRequest depositOperationRequest) {
        long amount = depositOperationRequest.getAmount();
        Account account = accountService.getByNumber(depositOperationRequest.getAccountNumber());
        accountService.deposit(account, depositOperationRequest.getPin(), amount);
        operationRepository.save(operationMapper.toOperation(amount, account, OperationType.DEPOSIT));
    }

    @Override
    @Transactional
    public void withdraw(WithdrawOperationRequest withdrawOperationRequest) {
        long amount = withdrawOperationRequest.getAmount();
        Account account = accountService.getByNumber(withdrawOperationRequest.getAccountNumber());
        accountService.withdraw(account, withdrawOperationRequest.getPin(), amount);
        operationRepository.save(operationMapper.toOperation(amount, account, OperationType.WITHDRAW));
    }

    @Override
    @Transactional
    public void transfer(TransferOperationRequest transferOperationRequest) {
        Account source = accountService.getByNumber(transferOperationRequest.getSourceAccountNumber());
        Account target = accountService.getByNumber(transferOperationRequest.getTargetAccountNumber());
        long amount = transferOperationRequest.getAmount();
        accountService.transfer(source, target, transferOperationRequest.getPin(), amount);
        operationRepository.save(operationMapper.toTransferOperation(amount, source, target));
    }

    @Override
    @Transactional
    public List<Operation> getOperationsByAccountNumber(String accountNumber) {
        return accountService.getByNumber(accountNumber).getOperations();
    }
}
