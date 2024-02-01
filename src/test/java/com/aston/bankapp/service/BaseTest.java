package com.aston.bankapp.service;

import com.aston.bankapp.dto.DepositOperationRequest;
import com.aston.bankapp.dto.TransferOperationRequest;
import com.aston.bankapp.dto.WithdrawOperationRequest;
import com.aston.bankapp.repository.AccountRepository;
import com.aston.bankapp.repository.OperationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@EnableJpaRepositories(basePackageClasses = {AccountRepository.class, OperationRepository.class})
@EntityScan(basePackages = "com.aston.bankapp.entity")
@DataJpaTest
@ContextConfiguration(classes = {JsonMapper.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BaseTest {

    @Autowired
    private ObjectMapper objectMapper;

    protected WithdrawOperationRequest getWithdrawRequest() throws IOException {
        return objectMapper.readValue(
                getClass().getClassLoader().getResourceAsStream("request/withdraw.json"),
                WithdrawOperationRequest.class
        );
    }

    protected DepositOperationRequest getDepositRequest() throws IOException {
        return objectMapper.readValue(
                getClass().getClassLoader().getResourceAsStream("request/deposit.json"),
                DepositOperationRequest.class
        );
    }

    protected TransferOperationRequest getTransferRequest() throws IOException {
        return objectMapper.readValue(
                getClass().getClassLoader().getResourceAsStream("request/transfer.json"),
                TransferOperationRequest.class
        );
    }
}
