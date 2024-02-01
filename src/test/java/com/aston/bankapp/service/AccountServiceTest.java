package com.aston.bankapp.service;

import com.aston.bankapp.dto.DepositOperationRequest;
import com.aston.bankapp.dto.TransferOperationRequest;
import com.aston.bankapp.dto.WithdrawOperationRequest;
import com.aston.bankapp.entity.Account;
import com.aston.bankapp.exception.InvalidPinException;
import com.aston.bankapp.repository.AccountRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ContextConfiguration(classes = {AccountServiceImpl.class})
public class AccountServiceTest extends BaseTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @MockBean
    private UserService userService;

    @Sql(scripts = {"classpath:sql/users.sql", "classpath:sql/accounts.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void testInvalidPin() throws IOException {
        DepositOperationRequest deposit = getDepositRequest();
        WithdrawOperationRequest withdraw = getWithdrawRequest();
        TransferOperationRequest transfer = getTransferRequest();

        Account depositAccount = accountService.getByNumber(deposit.getAccountNumber());
        Account withdrawAccount = accountService.getByNumber(withdraw.getAccountNumber());
        Account transferSourceAccount = accountService.getByNumber(transfer.getSourceAccountNumber());
        Account transferTargetAccount = accountService.getByNumber(transfer.getTargetAccountNumber());

        assertThatThrownBy(() -> accountService.deposit(depositAccount, "invalid", deposit.getAmount()))
                .isInstanceOf(InvalidPinException.class);
        assertThatThrownBy(() -> accountService.withdraw(withdrawAccount, "invalid", withdraw.getAmount()))
                .isInstanceOf(InvalidPinException.class);
        assertThatThrownBy(() -> accountService.transfer(transferSourceAccount, transferTargetAccount, "invalid", transfer.getAmount()))
                .isInstanceOf(InvalidPinException.class);
    }

    @Sql(scripts = {"classpath:sql/users.sql", "classpath:sql/accounts.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void testDepositIncreaseBalance() throws IOException {
        DepositOperationRequest deposit = getDepositRequest();
        Account account = accountService.getByNumber(deposit.getAccountNumber());
        long initialBalance = account.getBalance();
        accountService.deposit(account, deposit.getPin(), deposit.getAmount());
        long balance = accountRepository.findByNumber(deposit.getAccountNumber()).get().getBalance();

        assertThat(balance - initialBalance).isEqualTo(deposit.getAmount());
    }

    @Sql(scripts = {"classpath:sql/users.sql", "classpath:sql/accounts.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void testWithdrawDecreaseBalance() throws IOException {
        WithdrawOperationRequest withdraw = getWithdrawRequest();
        Account account = accountService.getByNumber(withdraw.getAccountNumber());
        long initialBalance = account.getBalance();
        accountService.withdraw(account, withdraw.getPin(), withdraw.getAmount());
        long balance = accountRepository.findByNumber(withdraw.getAccountNumber()).get().getBalance();

        assertThat(initialBalance - balance).isEqualTo(withdraw.getAmount());
    }

    @Sql(scripts = {"classpath:sql/users.sql", "classpath:sql/accounts.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void testWithdrawThrowsExceptionOnLowBalance() throws IOException {
        WithdrawOperationRequest withdraw = getWithdrawRequest();
        Account account = accountService.getByNumber(withdraw.getAccountNumber());
        assertThatThrownBy(() -> {
            accountService.withdraw(account, withdraw.getPin(), account.getBalance() + 1);
            accountRepository.flush();
        }).isInstanceOf(ValidationException.class);
    }

    @Sql(scripts = {"classpath:sql/users.sql", "classpath:sql/accounts.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void testTransferBalanceConsistent() throws IOException {
        TransferOperationRequest transferRequest = getTransferRequest();
        Account source = accountService.getByNumber(transferRequest.getSourceAccountNumber());
        Account target = accountService.getByNumber(transferRequest.getTargetAccountNumber());
        accountService.transfer(source, target, transferRequest.getPin(), transferRequest.getAmount());
        long targetBalance = accountService.getByNumber(transferRequest.getTargetAccountNumber()).getBalance();
        long sourceBalance = accountService.getByNumber(transferRequest.getSourceAccountNumber()).getBalance();

        assertThat(targetBalance - sourceBalance).isEqualTo(2 * transferRequest.getAmount());
    }

    @Sql(scripts = {"classpath:sql/users.sql", "classpath:sql/accounts.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void testTransferThrowsExceptionOnLowBalance() throws IOException {
        TransferOperationRequest transferRequest = getTransferRequest();
        Account source = accountService.getByNumber(transferRequest.getSourceAccountNumber());
        Account target = accountService.getByNumber(transferRequest.getTargetAccountNumber());

        assertThatThrownBy(() -> {
            accountService.transfer(source, target, transferRequest.getPin(), source.getBalance() + 1);
            accountRepository.flush();
        }).isInstanceOf(ValidationException.class);
    }
}
