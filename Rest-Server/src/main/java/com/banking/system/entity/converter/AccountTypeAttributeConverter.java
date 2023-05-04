package com.banking.system.entity.converter;


import com.banking.system.entity.converter.enums.AccountType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AccountTypeAttributeConverter implements AttributeConverter<AccountType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AccountType attribute) {
        if (attribute == null)
            return null;

        switch (attribute) {
            case SAVING:
                return 1;
            case SALARY:
                return 2;

            default:
                throw new IllegalArgumentException(attribute + " not supported.");
        }
    }

    @Override
    public AccountType convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;

        switch (dbData) {
            case 1:
                return AccountType.SAVING;
            case 2:
                return AccountType.SALARY;
            default:
                throw new IllegalArgumentException(dbData + " not supported.");
        }
    }

}