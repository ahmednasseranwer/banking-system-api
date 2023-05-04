package com.banking.system.entity;

import com.banking.system.entity.converter.AccountStatusAttributeConverter;
import com.banking.system.entity.converter.AccountTypeAttributeConverter;
import com.banking.system.entity.converter.enums.AccountStatus;
import com.banking.system.entity.converter.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(initialValue = 0071111134, name = "account_id_gen", sequenceName = "account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_gen")
    private Long id;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "account_status", nullable = false)
    @Convert(converter = AccountStatusAttributeConverter.class)
    private AccountStatus accountStatus;

    @Column(name = "account_type", nullable = false)
    @Convert(converter = AccountTypeAttributeConverter.class)
    private AccountType accountType;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(cascade={CascadeType.ALL},
                mappedBy = "account",
                fetch = FetchType.EAGER)
    @OrderBy("created_at DESC")
    private List<Transaction> transactions;

}
