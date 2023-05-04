package com.banking.system.service.impl;

import com.banking.system.entity.Account;
import com.banking.system.entity.Transaction;
import com.banking.system.entity.converter.enums.TransactionType;
import com.banking.system.exception.custom.NotFoundException;
import com.banking.system.mapper.BankAccountMapper;
import com.bank.system.model.BankAccountRequest;
import com.bank.system.model.BankAccountResponse;
import com.bank.system.model.TransactionsResponse;
import com.bank.system.model.TransferModel;
import com.banking.system.repository.AccountRepository;
import com.banking.system.service.BankAccountService;
import com.banking.system.validator.BankAccountValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountMapper bankAccountMapper = Mappers.getMapper(BankAccountMapper.class);

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BankAccountValidator bankAccountValidator;

    @Override
    public Long createBankAccount(BankAccountRequest bankAccount) {
        Account account = bankAccountMapper.toAccountEntity(bankAccount);
        return accountRepository.save(account).getId();
    }

    @Override
    public BankAccountResponse getBankAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(NotFoundException::new);
        return bankAccountMapper.toBankAccount(account);
    }

    @Override
    @Transactional
    public void transfer(TransferModel transferModel) {

        synchronized ( this) {

            Account fromAccount = accountRepository.findById(transferModel.getFromAccount()).orElseThrow(NotFoundException::new);
            Account toAccount = accountRepository.findById(transferModel.getToAccount()).orElseThrow(NotFoundException::new);
        }
       bankAccountValidator.validateAccounts(fromAccount,toAccount,transferModel.getAmount());
       Long balanceFromAccountAfterTransfer =  fromAccount.getBalance()-transferModel.getAmount();
       Long balanceToAccountAfterTransfer = toAccount.getBalance()+transferModel.getAmount();
       runSenderTransaction(transferModel.getTransactionType(),fromAccount,toAccount,balanceFromAccountAfterTransfer);
       runReceiverTransaction(transferModel.getTransactionType(),fromAccount,toAccount,balanceToAccountAfterTransfer);
    }

    @Override
    public List<TransactionsResponse> getTransactions(Long accountId) {
        List<Transaction> transaction = accountRepository.findById(accountId).orElseThrow(NotFoundException::new)
                .getTransactions();
        return bankAccountMapper.toTransactionList(transaction);
    }

    private void runReceiverTransaction(String transactionType, Account fromAccount, Account toAccount, Long balanceToAccountAfterTransfer) {
        Transaction transaction= createTransaction(transactionType,fromAccount,toAccount,toAccount,balanceToAccountAfterTransfer);
        fromAccount.getTransactions().add(transaction);

        transaction.setAmountBefore(toAccount.getBalance());
        toAccount.setBalance(balanceToAccountAfterTransfer);
        accountRepository.save(toAccount);
    }

    private void runSenderTransaction(String transactionType, Account fromAccount, Account toAccount, Long balanceFromAccountAfterTransfer) {
        Transaction transaction= createTransaction(transactionType,fromAccount,toAccount,fromAccount,balanceFromAccountAfterTransfer);
             fromAccount.getTransactions().add(transaction);

        transaction.setAmountBefore(fromAccount.getBalance());
        fromAccount.setBalance(balanceFromAccountAfterTransfer);
        accountRepository.save(fromAccount);
    }

    private Transaction createTransaction(String transactionType,Account fromAccount, Account toAccount, Account account, Long balanceToUpdate){
        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAccount(account);
        transaction.setAmountAfter(balanceToUpdate);
        transaction.setTransactionType(TransactionType.valueOf(transactionType));
        return transaction;
    }

}
