package com.banking.system.mapper;

import com.banking.system.entity.Account;
import com.banking.system.entity.Transaction;
import com.bank.system.model.BankAccountRequest;
import com.bank.system.model.BankAccountResponse;
import com.bank.system.model.TransactionsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface BankAccountMapper {

     @Mapping(target = "customer.id", source = "customerId")
     Account toAccountEntity(BankAccountRequest bankAccount);

     @Mapping(source = "customer.id", target = "customerId")
     BankAccountResponse toBankAccount(Account account);
     List<TransactionsResponse>  toTransactionList(List<Transaction> transactionListEntities);

     @Mapping(source = "fromAccount.id", target = "fromAccount")
     @Mapping(source = "toAccount.id", target = "toAccount")
     TransactionsResponse  toTransaction(Transaction transactionEntity);


}
