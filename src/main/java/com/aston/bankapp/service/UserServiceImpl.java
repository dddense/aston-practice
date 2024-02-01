package com.aston.bankapp.service;

import com.aston.bankapp.dto.UserCreationRequest;
import com.aston.bankapp.entity.User;
import com.aston.bankapp.exception.NotFoundException;
import com.aston.bankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public UUID save(UserCreationRequest userCreationRequest) {
        return userRepository.save(
                User.builder()
                        .phoneNumber(userCreationRequest.phoneNumber())
                        .name(userCreationRequest.name())
                        .build()
        ).getId();
    }
}
