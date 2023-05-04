package com.banking.system.entity;

import com.banking.system.entity.converter.enums.TransactionType;
import com.banking.system.entity.converter.TransactionTypeAttributeConverter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_Type", nullable = false)
    @Convert(converter = TransactionTypeAttributeConverter.class)
    private TransactionType transactionType;

    @Column(name = "amount_before", nullable = false)
    private Long amountBefore;

    @Column(name = "amount_after", nullable = false)
    private Long amountAfter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id",referencedColumnName = "id",nullable = false)
    private Account fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id",referencedColumnName = "id",nullable = false)
    private Account toAccount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id",referencedColumnName = "id",nullable = false)
    private Account account;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();
}
