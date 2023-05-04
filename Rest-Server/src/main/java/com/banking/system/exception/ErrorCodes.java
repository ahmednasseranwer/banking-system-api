package com.banking.system.exception;

import com.bank.system.model.ErrorApiResponse;

public class ErrorCodes {

    private ErrorCodes(){
        throw new IllegalStateException("ErrorCodes class");
    }
    public static final  ErrorApiResponse NOT_HAS_ENOUGH_MONEY = new ErrorApiResponse(1001,"No has enough code to transfer");
    public static final ErrorApiResponse ACCOUNT_NOT_ACTIVE_STATUS = new ErrorApiResponse(1002,"Your account should be active status");
    public static final ErrorApiResponse CURRENCY_SHOULD_SAME = new ErrorApiResponse(1003,"The currency should be the same");

}
