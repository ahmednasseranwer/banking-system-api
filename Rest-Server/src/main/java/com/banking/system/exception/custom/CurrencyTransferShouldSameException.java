package com.banking.system.exception.custom;


import static com.banking.system.exception.ErrorCodes.CURRENCY_SHOULD_SAME;

public class CurrencyTransferShouldSameException extends ProblemRunTimeException {

    public CurrencyTransferShouldSameException() {
        super(CURRENCY_SHOULD_SAME);
    }
}
