package com.banking.system.entity.converter;


import com.banking.system.entity.converter.enums.AccountStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AccountStatusAttributeConverter implements AttributeConverter<AccountStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AccountStatus attribute) {
        if (attribute == null)
            return null;

        switch (attribute) {
            case ACTIVE:
                return 1;
            case PENDING:
                return 2;
            case SUSPENDED:
                return 3;
            case DELETED:
                return 4;
            default:
                throw new IllegalArgumentException(attribute + " not supported.");
        }
    }

    @Override
    public AccountStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;

        switch (dbData) {
            case 1:
                return AccountStatus.ACTIVE;
            case 2:
                return AccountStatus.PENDING;
            case 3:
                return AccountStatus.SUSPENDED;
            case 4:
                return AccountStatus.DELETED;
            default:
                throw new IllegalArgumentException(dbData + " not supported.");
        }
    }

}