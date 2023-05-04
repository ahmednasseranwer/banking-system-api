package com.banking.system.exception.custom;


import static com.banking.system.exception.ErrorCodes.ACCOUNT_NOT_ACTIVE_STATUS;

public class AccountNotActiveStatusException extends ProblemRunTimeException {

    public AccountNotActiveStatusException() {
        super(ACCOUNT_NOT_ACTIVE_STATUS);
    }
}
