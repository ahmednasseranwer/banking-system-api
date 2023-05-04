package com.banking.system.entity.converter;

import com.banking.system.entity.converter.enums.TransactionType;

import javax.persistence.AttributeConverter;

public class TransactionTypeAttributeConverter implements AttributeConverter<TransactionType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TransactionType attribute) {
        if (attribute == null)
            return null;

        switch (attribute) {
            case DEPOSITS:
                return 1;
            case ONLINE_PAYMENTS:
                return 2;
            case OTHERS:
                return 3;
            default:
                throw new IllegalArgumentException(attribute + " not supported.");
        }
    }

    @Override
    public TransactionType convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;

        switch (dbData) {
            case 1:
                return TransactionType.DEPOSITS;
            case 2:
                return TransactionType.ONLINE_PAYMENTS;
            case 3:
                return TransactionType.OTHERS;
            default:
                throw new IllegalArgumentException(dbData + " not supported.");
        }
    }

}
