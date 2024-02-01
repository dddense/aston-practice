package com.aston.bankapp.mapper;

import com.aston.bankapp.dto.AccountResponse;
import com.aston.bankapp.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "beneficiaryName", source = "entity.user.name")
    AccountResponse toDto(Account entity);

    List<AccountResponse> toDto(Collection<Account> entities);
}
