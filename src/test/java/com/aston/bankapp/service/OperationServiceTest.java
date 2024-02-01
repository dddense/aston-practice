package com.aston.bankapp.service;

import com.aston.bankapp.dto.DepositOperationRequest;
import com.aston.bankapp.dto.TransferOperationRequest;
import com.aston.bankapp.dto.WithdrawOperationRequest;
import com.aston.bankapp.entity.Operation;
import com.aston.bankapp.enumeration.OperationType;
import com.aston.bankapp.mapper.OperationMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {OperationServiceImpl.class, AccountServiceImpl.class, OperationMapperImpl.class})
public class OperationServiceTest extends BaseTest {

    @Autowired
    private OperationService operationService;

    @MockBean
    private UserService userService;

    @Sql(scripts = {"classpath:sql/users.sql", "classpath:sql/accounts.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void testDepositOperationIncreaseBalance() throws IOException {
        DepositOperationRequest deposit = getDepositRequest();
        operationService.deposit(deposit);
        List<Operation> operations = operationService.getOperationsByAccountNumber(deposit.getAccountNumber());
        assertThat(operations).hasSize(1);
        assertThat(operations.get(0).getType()).isEqualTo(OperationType.DEPOSIT);
    }

    @Sql(scripts = {"classpath:sql/users.sql", "classpath:sql/accounts.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void testWithdrawOperationDecreaseBalance() throws IOException {
        WithdrawOperationRequest withdraw = getWithdrawRequest();
        operationService.withdraw(withdraw);
        List<Operation> operations = operationService.getOperationsByAccountNumber(withdraw.getAccountNumber());
        assertThat(operations).hasSize(1);
        assertThat(operations.get(0).getType()).isEqualTo(OperationType.WITHDRAW);
    }

    @Sql(scripts = {"classpath:sql/users.sql", "classpath:sql/accounts.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void testTransferOperationBalanceConsistent() throws IOException {
        TransferOperationRequest transferRequest = getTransferRequest();
        operationService.transfer(transferRequest);
        List<Operation> sourceAccountOperations = operationService.getOperationsByAccountNumber(transferRequest.getSourceAccountNumber());
        assertThat(sourceAccountOperations).hasSize(1);
        assertThat(sourceAccountOperations.get(0).getType()).isEqualTo(OperationType.TRANSFER);
        List<Operation> targetAccountOperations = operationService.getOperationsByAccountNumber(transferRequest.getTargetAccountNumber());
        assertThat(targetAccountOperations).hasSize(0);
    }
}
