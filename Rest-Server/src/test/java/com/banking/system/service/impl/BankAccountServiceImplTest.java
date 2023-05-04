package com.banking.system.service.impl;

import com.bank.system.model.BankAccountResponse;
import com.bank.system.model.TransactionsResponse;
import com.banking.system.exception.custom.AccountNotActiveStatusException;
import com.banking.system.exception.custom.NotFoundException;
import com.banking.system.repository.AccountRepository;
import com.banking.system.validator.BankAccountValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.banking.system.fixture.AccountFixture.bankAccountRequest;
import static com.banking.system.fixture.AccountFixture.createdAccount;
import static com.banking.system.fixture.BankAccountFixture.*;
import static com.banking.system.fixture.TransferModelFixture.transferModel;
import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class BankAccountServiceImplTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    BankAccountValidator bankAccountValidator;

    @InjectMocks
    BankAccountServiceImpl bankAccountServiceImpl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("when the created bank account saved successful on Database")
    public void testCreateBankAccount_whenAccountSavedSuccessfulOnDatabase(){
        when(accountRepository.save(any())).thenReturn(createdAccount());
        Long accountId = bankAccountServiceImpl.createBankAccount(bankAccountRequest());
        assertEquals(111123124L,accountId.longValue());
    }

    @Test
    @DisplayName("when trying get a wrong accountId not in Account table")
    public void testGetBankAccount_whenNotFoundAccount() {
        when(accountRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()-> bankAccountServiceImpl.getBankAccount(anyLong()));
    }

    @Test
    @DisplayName("when trying get bank account saved successful on Database")
    public void testGetBankAccount_whenAccountSavedSuccessfulOnDatabase() {
        when(accountRepository.findById(any())).thenReturn(Optional.of(createdAccount()));
        BankAccountResponse bankAccount = bankAccountServiceImpl.getBankAccount(anyLong());
        assertEquals(createdAccount().getBalance(),bankAccount.getBalance());
        assertEquals(createdAccount().getAccountType().toString(),bankAccount.getAccountType());
        assertEquals(createdAccount().getAccountStatus().toString(),bankAccount.getAccountStatus());
        assertEquals(createdAccount().getCurrency(),bankAccount.getCurrency());
        assertEquals(createdAccount().getId(),bankAccount.getId());
    }

    @Test
    @DisplayName("when trying get transaction for given account id")
    public void testGetTransaction_whenTheAccountHasTransactionSavedSuccessfulOnDatabase() {
        when(accountRepository.findById(any())).thenReturn(Optional.of(createdAccount()));
        List<TransactionsResponse> transactionsResponseList = bankAccountServiceImpl.getTransactions(anyLong());
        assertEquals(createdAccount().getTransactions().get(0).getToAccount().getId(),transactionsResponseList.get(0).getToAccount());
        assertEquals(createdAccount().getTransactions().get(0).getAmountAfter(),transactionsResponseList.get(0).getAmountAfter());
        assertEquals(createdAccount().getTransactions().get(0).getId(),transactionsResponseList.get(0).getId());
        assertEquals(createdAccount().getTransactions().get(0).getAmountBefore(),transactionsResponseList.get(0).getAmountBefore());
        assertEquals(createdAccount().getTransactions().get(0).getFromAccount().getId(),transactionsResponseList.get(0).getFromAccount());
    }

    @Test
    @DisplayName("when trying get transaction for wrong account id")
    public void testGetTransaction_whenTheWrongAccountId() {
        when(accountRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()-> bankAccountServiceImpl.getTransactions(anyLong()));
    }

    @Test
    @DisplayName("when transfer money from wrong sender account")
    public void testRefund_whenNotFoundFromAccount() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()-> bankAccountServiceImpl.transfer(transferModel()));
    }

    @Test
    @DisplayName("when transfer money from wrong receiver account")
    public void testRefund_whenNotFoundToAccount() {
        when(accountRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, ()-> bankAccountServiceImpl.transfer(transferModel()));
    }

    @Test
    @DisplayName("when transfer money from sender without any exception - success case")
    public void testRefund_whenNotActiveSenderAccountStatus() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(ACCOUNT_5000EGP()));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(ACCOUNT_200EGP()));
        when(accountRepository.save(any())).thenReturn(ACCOUNT_5000EGP());

        doNothing().when(bankAccountValidator).validateAccounts(any(),any(),anyLong());
        assertDoesNotThrow(()-> bankAccountServiceImpl.transfer(transferModel()));
    }



}
