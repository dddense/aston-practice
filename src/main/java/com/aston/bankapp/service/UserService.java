package com.aston.bankapp.service;

import com.aston.bankapp.dto.UserCreationRequest;
import com.aston.bankapp.entity.User;

import java.util.UUID;

public interface UserService {

    User getByPhoneNumber(String phoneNumber);

    UUID save(UserCreationRequest userCreationRequest);
}
