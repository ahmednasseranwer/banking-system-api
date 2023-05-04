package com.banking.system.validator;


import com.banking.system.exception.custom.AccountNotActiveStatusException;
import com.banking.system.exception.custom.CurrencyTransferShouldSameException;
import com.banking.system.exception.custom.NotHasEnoughMoneyToTransferException;
import com.banking.system.exception.custom.ProblemRunTimeException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static com.banking.system.fixture.BankAccountFixture.*;
import static org.junit.jupiter.api.Assertions.*;

public class BankAccountValidatorTest {

    BankAccountValidator bankAccountValidator= new BankAccountValidator();

    @Test
    @DisplayName("Test validateAccounts when the sender account not have enough money equivalent to amout to transfer")
    public void testValidateAccounts_whenSenderAccountNotHasEnoughMoney(){
        ProblemRunTimeException exception = assertThrows(NotHasEnoughMoneyToTransferException.class, ()-> bankAccountValidator.validateAccounts(ACCOUNT_200EGP(),ACCOUNT_5000EGP(),1000L));
        assertEquals("No has enough code to transfer", exception.getErrorApiResponse().getMessage());
        assertEquals(1001, exception.getErrorApiResponse().getCode());
    }

    @Test
    @DisplayName("Test validateAccounts when the sender account without ACTIVE status account")
    public void testValidateAccounts_whenSenderNotActiveStatus(){
        ProblemRunTimeException exception = assertThrows(AccountNotActiveStatusException.class, ()-> bankAccountValidator.validateAccounts(ACCOUNT_5000EGP_SUSPENDED(),ACCOUNT_5000EGP(),1000L));
        assertEquals("Your account should be active status", exception.getErrorApiResponse().getMessage());
        assertEquals(1002, exception.getErrorApiResponse().getCode());
    }

    @Test
    @DisplayName("Test validateAccounts when the transfer currency not the same")
    public void testValidateAccounts_whenCurrencyTransferShouldSame(){
        ProblemRunTimeException exception = assertThrows(CurrencyTransferShouldSameException.class, ()-> bankAccountValidator.validateAccounts(ACCOUNT_5000USD(),ACCOUNT_5000EGP(),1000L));
        assertEquals("The currency should be the same", exception.getErrorApiResponse().getMessage());
        assertEquals(1003, exception.getErrorApiResponse().getCode());
    }

    @Test
    @DisplayName("Test validateAccounts when currency is the same, sender active status, and the sender has balance enough to transfer money")
    public void testValidateAccounts_ValidationOK_WithoutException(){
        assertDoesNotThrow( ()-> bankAccountValidator.validateAccounts(ACCOUNT_5000EGP(),ACCOUNT_200EGP(),1000L));
    }
}
