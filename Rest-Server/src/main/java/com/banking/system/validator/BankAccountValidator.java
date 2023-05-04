package com.banking.system.validator;

import com.banking.system.entity.Account;
import com.banking.system.entity.converter.enums.AccountStatus;
import com.banking.system.exception.custom.AccountNotActiveStatusException;
import com.banking.system.exception.custom.CurrencyTransferShouldSameException;
import com.banking.system.exception.custom.NotHasEnoughMoneyToTransferException;
import org.springframework.stereotype.Component;

@Component
public class BankAccountValidator {

    public void validateAccounts(Account fromAccount,Account toAccount,Long amount){
        senderHasEnoughMoney(fromAccount.getBalance(),amount);
        senderNotAccountActive(fromAccount.getAccountStatus());
        currencyShouldSame(fromAccount.getCurrency(),toAccount.getCurrency());
    }

    private void currencyShouldSame(String senderCurrency, String receiverCurrency) {
        if(!senderCurrency.equals(receiverCurrency)){
            throw new CurrencyTransferShouldSameException();
        }
    }

    private void senderNotAccountActive(AccountStatus accountStatus) {
        if(!AccountStatus.ACTIVE.equals(accountStatus)){
            throw new AccountNotActiveStatusException();
        }
    }

    private void senderHasEnoughMoney(Long balance, Long amount) {
        if(amount>balance)
            throw new NotHasEnoughMoneyToTransferException();
    }

}
