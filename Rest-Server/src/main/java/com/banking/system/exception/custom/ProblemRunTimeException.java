package com.banking.system.exception.custom;

import com.bank.system.model.ErrorApiResponse;
import lombok.Getter;

@Getter
public class ProblemRunTimeException extends RuntimeException{

    public final ErrorApiResponse errorApiResponse;
    public ProblemRunTimeException(ErrorApiResponse errorApiResponse)
    {
        super(errorApiResponse.getMessage());
        this.errorApiResponse= errorApiResponse;
    }



}
