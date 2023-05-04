package com.banking.system.fixture;

import com.bank.system.model.BankAccountRequest;
import com.bank.system.model.BankAccountResponse;
import com.banking.system.entity.Account;
import com.banking.system.entity.Customer;
import com.banking.system.entity.Transaction;
import com.banking.system.entity.converter.enums.AccountStatus;
import com.banking.system.entity.converter.enums.AccountType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountFixture {

   public static final Account createdAccount(){
       Account account = new Account();
       account.setAccountType(AccountType.SAVING);
       account.setAccountStatus(AccountStatus.ACTIVE);
       account.setCreatedAt(LocalDateTime.now());
       account.setBalance(3000L);
       account.setCurrency("EGP");
       account.setId(111123124L);
       Customer customer = new Customer();
       customer.setId(123123L);
       customer.setName("Ali Ali");
       account.setCustomer(customer);
       List<Transaction> transactionList = new ArrayList<>();
       Transaction transaction = new Transaction();
       transaction.setAccount(account);
       transaction.setFromAccount(account);
       transaction.setToAccount(account);
       transaction.setId(234234L);
       transaction.setAmountAfter(2000L);
       transaction.setAmountBefore(1000L);
       transactionList.add(transaction);
       account.setTransactions(transactionList);
       return account;
   }

    public static final BankAccountRequest bankAccountRequest(){
        BankAccountRequest account = new BankAccountRequest();
        account.setAccountType(AccountType.SAVING.toString());
        account.setCurrency("EGP");
        account.setBalance(3000L);
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Ali Ali");
        account.setCustomerId(1L);
        account.setAccountStatus(AccountStatus.ACTIVE.toString());
        return account;
    }

    public static final BankAccountResponse bankAccountResponse(){
        BankAccountResponse account = new BankAccountResponse();
        account.setAccountType(AccountType.SAVING.toString());
        account.setCurrency("EGP");
        account.setBalance(3000L);
        account.setId(1L);
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Ali Ali");
        account.setCustomerId(1L);
        account.setAccountStatus(AccountStatus.ACTIVE.toString());
        return account;
    }
    public static final BankAccountRequest bankAccountRequestWithNullAccountType(){
        BankAccountRequest account = new BankAccountRequest();
        account.setCurrency("EGP");
        account.setBalance(3000L);
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Ali Ali");
        account.setCustomerId(1L);
        account.setAccountStatus(AccountStatus.ACTIVE.toString());
        account.setAccountType(null);
        return account;
    }


}
