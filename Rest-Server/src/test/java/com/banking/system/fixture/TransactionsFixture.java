package com.banking.system.fixture;

import com.bank.system.model.TransactionsResponse;
import com.banking.system.entity.Account;
import com.banking.system.entity.converter.enums.AccountStatus;
import com.banking.system.entity.converter.enums.AccountType;
import com.banking.system.entity.converter.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionsFixture {

    public static final List<TransactionsResponse> transactionListResponse(){
        List<TransactionsResponse> transactionsResponseList = new ArrayList<>();
        TransactionsResponse transactionsResponse = new TransactionsResponse();
        transactionsResponse.setTransactionType(TransactionType.DEPOSITS.toString());
        transactionsResponse.setAmountAfter(1000L);
        transactionsResponse.setAmountBefore(2000L);
        transactionsResponse.setFromAccount(12323L);
        transactionsResponse.setToAccount(123123L);
        transactionsResponse.setId(222L);
        transactionsResponse.setCreatedAt(LocalDateTime.now());
        transactionsResponseList.add(transactionsResponse);
        return transactionsResponseList;
    }

}
