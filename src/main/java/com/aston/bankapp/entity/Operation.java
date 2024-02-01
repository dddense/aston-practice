package com.aston.bankapp.entity;

import com.aston.bankapp.enumeration.OperationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "operations")
public class Operation {

    @Id
    @Column(name = "id")
    private UUID id;

    /**
     * Сумма в минорных единицах, например, в копейках
     */
    @Range(min = 0)
    @Column(name = "amount")
    private long amount;

    @Column(name = "operation_type")
    @Enumerated(EnumType.STRING)
    private OperationType type;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JoinColumn(name = "account_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID();
    }
}
