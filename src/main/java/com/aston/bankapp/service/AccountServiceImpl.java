package com.aston.bankapp.service;

import com.aston.bankapp.dto.AccountCreationRequest;
import com.aston.bankapp.entity.Account;
import com.aston.bankapp.exception.InvalidPinException;
import com.aston.bankapp.exception.NotFoundException;
import com.aston.bankapp.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final UserService userService;

    @Override
    public Account createAccount(AccountCreationRequest accountCreationRequest) {
        return accountRepository.save(
                Account.builder()
                        .pin(accountCreationRequest.pin())
                        .user(userService.getByPhoneNumber(accountCreationRequest.phoneNumber()))
                        .build());
    }

    @Override
    public List<Account> getAccounts(String phoneNumber) {
        return accountRepository.findAllByUser_Id(userService.getByPhoneNumber(phoneNumber).getId());
    }

    @Override
    public Account getByNumber(String number) {
        return accountRepository.findByNumber(number)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    @Override
    public void deposit(Account account, String pin, long amount) {
        validatePin(pin, account);
        accountRepository.save(account.increaseBalance(amount));
    }

    @Override
    public void withdraw(Account account, String pin, long amount) {
        validatePin(pin, account);
        accountRepository.save(account.decreaseBalance(amount));
    }

    @Override
    public void transfer(Account source, Account target, String pin, long amount) {
        withdraw(source, pin, amount);
        accountRepository.save(target.increaseBalance(amount));
    }

    @Override
    public void validatePin(String pin, Account account) {
        if (!account.getPin().equals(pin)) {
            throw new InvalidPinException("Wrong pin");
        }
    }
}
