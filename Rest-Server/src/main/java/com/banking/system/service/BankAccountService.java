package com.banking.system.service;

import com.bank.system.model.BankAccountRequest;
import com.bank.system.model.BankAccountResponse;
import com.bank.system.model.TransactionsResponse;
import com.bank.system.model.TransferModel;

import java.util.List;

public interface BankAccountService {

    /**
     * bank-account : create bank account
     * create a new bank account associate to given customer id
     *
     * @param bankAccount (required) contains accountStatus, accountType, currency, customerId and balance
     *                    all fields is mandatory
     * @return account-id of created account
     */
    Long createBankAccount(BankAccountRequest bankAccount);

    /**
     * getBankAccount : get account by id
     *
     * @param accountId
     *
     * @return the saved BankAccount of account id
     *  or throw NotFoundException()
     */
    BankAccountResponse getBankAccount(Long accountId);

    /**
     * transfer: transfer money from bank account to another account
     *    needs to make sure same currency, the sender have available money to transfer with active status
     *    save the transaction history with the amount before and after of two accounts
     *    update the balance of two accounts
     * @param transferModel (required) contains fromAccount, toAccount, transactionType
     *                    all fields is mandatory
     * @return
     */
    void transfer(TransferModel transferModel);

    /**
     * getTransactions: Retrieve transactions history for a given account.
     *      the transaction contains the sender and receiver and amount before and after every transaction
     *      with transaction Type and currency ordered by createdAt date
     *
     * @param accountId (required) gived account id
     *
     * @return List of Transaction ordered by created at DESC
     */
    List<TransactionsResponse> getTransactions(Long accountId);
}
