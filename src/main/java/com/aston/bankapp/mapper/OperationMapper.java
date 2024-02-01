package com.aston.bankapp.mapper;

import com.aston.bankapp.dto.OperationResponse;
import com.aston.bankapp.dto.TransferOperationRequest;
import com.aston.bankapp.entity.Account;
import com.aston.bankapp.entity.Operation;
import com.aston.bankapp.entity.TransferOperation;
import com.aston.bankapp.enumeration.OperationType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Operation toOperation(long amount, Account account, OperationType type);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "type", expression = "java(OperationType.TRANSFER)")
    TransferOperation toTransferOperation(long amount, Account account, Account targetAccount);

    @Mapping(target = "target", qualifiedByName = "mapOperationTarget", source = "operation")
    OperationResponse toDto(Operation operation);

    List<OperationResponse> toDto(Collection<Operation> operations);

    @Named("mapOperationTarget")
    default String mapTarget(Operation operation) {
        return switch (operation.getType()) {
            case TRANSFER -> ((TransferOperation) operation).getTargetAccount().getUser().getName();
            case DEPOSIT -> "Пополнение счета";
            case WITHDRAW -> "Вывод средств";
        };
    }
}
