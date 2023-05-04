package com.banking.system.controller;

import com.bank.system.model.BankAccountRequest;
import com.bank.system.model.BankAccountResponse;
import com.bank.system.model.TransactionsResponse;
import com.bank.system.model.TransferModel;
import com.banking.system.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/")
public class BankAccountController  {


    @Autowired

    BankAccountService bankAccountServiceImpl;

    /**
     * POST /bank-account : create bank account
     * create a new bank account associate to given customer id
     *
     * @param bankAccount (required) contains accountStatus, accountType, currency, customerId and balance
     *                    all fields is mandatory
     * @return Successful operation with empty body (status code 200) if it saved successful
     *               headers uri reference with created account
     *         Bad Request (status code 400) if any problem in request body
     */
    @PostMapping(value = "/bank-account",
            produces = {"application/json"},
            consumes = {"application/json"})
    public ResponseEntity<Void> createBankAccount(@Valid @RequestBody BankAccountRequest bankAccount) {
        Long accountId = bankAccountServiceImpl.createBankAccount(bankAccount);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("accountId", accountId.toString());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{accountId}")
                .buildAndExpand(accountId)
                .toUri();

       return  ResponseEntity.created(location).headers(responseHeaders).build();
    }


    /**
     * GET /bank-account/{accountId} : get bank account by id
     * get bank account
     *
     * @param accountId ID of account to get (required)
     * @return BankAccountResponse the saved account details successful operation (status code 200)
     * or Not Found (status code 404)
     */
    @GetMapping(value = "/bank-account/{accountId}",
            produces = {"application/json"})
    public ResponseEntity<BankAccountResponse> getBankAccount(@PathVariable("accountId") Long accountId) {
        return new ResponseEntity<>(bankAccountServiceImpl.getBankAccount(accountId), HttpStatus.OK);
    }


    /**
     * POST /bank-account/transfer : transfer amount
     * transfer money from bank account to another account
     * needs to make sure same currency, the sender have available money to transfer with active status
     * save the transaction history with the amount before and after in two accounts
     *
     * @param transferModel (required)
     * @return successful operation (status code 200)
     * or Bad Request (status code 400)
     */
    @PostMapping(value = "/bank-account/transfer",
            produces = {"application/json"},
            consumes = {"application/json"})
    public ResponseEntity<Void> transferMoney(@Valid @RequestBody TransferModel transferModel) {
        bankAccountServiceImpl.transfer(transferModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET /bank-account/{accountId}/transactions : transactions for a given account.
     * Retrieve transactions history for a given account.
     * the transaction contains the sender and receiver and amount before and after every transaction
     * with transaction Type and currency ordered by createdAt date
     *
     * @param accountId ID of session to get (required)
     * @return List<TransactionsResponse> successful operation ordered by createdAt (status code 200)
     * or Not Found (status code 404)
     */

    @GetMapping(value = "/bank-account/{accountId}/transactions",
            produces = {"application/json"})
    public ResponseEntity<List<TransactionsResponse>> getBankAccountTransaction(@PathVariable("accountId") Long accountId) {

        return new ResponseEntity<>(bankAccountServiceImpl.getTransactions(accountId), HttpStatus.OK);
    }

}
