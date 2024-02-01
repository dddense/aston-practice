package com.aston.bankapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank_accounts")
public class Account {

    @Id
    @Column(name = "id")
    private UUID id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "number")
    @Builder.Default
    private String number = UUID.randomUUID().toString();

    /**
     * Баланс счета в минорных единицах, например, в копейках
     */
    @Range(min = 0)
    @Column(name = "balance")
    @Builder.Default
    private long balance = 0;

    @Length(min = 4, max = 4)
    @Column(name = "pin")
    private String pin;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "account")
    private List<Operation> operations = new ArrayList<>();

    public Account increaseBalance(long amount) {
        this.balance += amount;
        return this;
    }

    public Account decreaseBalance(long amount) {
        this.balance -= amount;
        return this;
    }

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID();
    }
}
