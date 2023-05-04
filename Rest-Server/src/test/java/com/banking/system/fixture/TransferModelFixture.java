package com.banking.system.fixture;

import com.bank.system.model.TransferModel;
import com.banking.system.entity.converter.enums.TransactionType;

public class TransferModelFixture {

  public static TransferModel transferModel(){
      TransferModel transferModel = new TransferModel();
      transferModel.setAmount(500L);
      transferModel.setFromAccount(1L);
      transferModel.setToAccount(2L);
      transferModel.setTransactionType(TransactionType.DEPOSITS.toString());
      return transferModel;
  }
}
