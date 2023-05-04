package com.banking.system.fixture;

import com.banking.system.entity.Account;
import com.banking.system.entity.converter.enums.AccountStatus;
import com.banking.system.entity.converter.enums.AccountType;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BankAccountFixture {

    public static final Account ACCOUNT_200EGP(){
        Account fromAccount = new Account();
        fromAccount.setAccountStatus(AccountStatus.ACTIVE);
        fromAccount.setBalance(200L);
        fromAccount.setCurrency("EGP");
        fromAccount.setCreatedAt(LocalDateTime.now());
        fromAccount.setAccountType(AccountType.SAVING);
        fromAccount.setTransactions(new ArrayList<>());

        return fromAccount;
    }

    public static final Account ACCOUNT_5000EGP(){
        Account toAccount = new Account();
        toAccount.setAccountStatus(AccountStatus.ACTIVE);
        toAccount.setBalance(5000L);
        toAccount.setCurrency("EGP");
        toAccount.setCreatedAt(LocalDateTime.now());
        toAccount.setAccountType(AccountType.SAVING);
        toAccount.setTransactions(new ArrayList<>());
        return toAccount;
    }

    public static final Account ACCOUNT_5000USD(){
        Account toAccount = new Account();
        toAccount.setAccountStatus(AccountStatus.ACTIVE);
        toAccount.setBalance(5000L);
        toAccount.setCurrency("USD");
        toAccount.setCreatedAt(LocalDateTime.now());
        toAccount.setAccountType(AccountType.SAVING);
        toAccount.setTransactions(new ArrayList<>());

        return toAccount;
    }
    public static final Account ACCOUNT_5000EGP_SUSPENDED(){
        Account toAccount = new Account();
        toAccount.setAccountStatus(AccountStatus.SUSPENDED);
        toAccount.setBalance(5000L);
        toAccount.setCurrency("EGP");
        toAccount.setCreatedAt(LocalDateTime.now());
        toAccount.setAccountType(AccountType.SAVING);
        toAccount.setTransactions(new ArrayList<>());

        return toAccount;
    }
}
