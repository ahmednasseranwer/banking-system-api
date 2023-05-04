package com.banking.system.exception.custom;

import static com.banking.system.exception.ErrorCodes.NOT_HAS_ENOUGH_MONEY;

public class NotHasEnoughMoneyToTransferException extends ProblemRunTimeException{

    public NotHasEnoughMoneyToTransferException() {
        super(NOT_HAS_ENOUGH_MONEY);
    }
}
