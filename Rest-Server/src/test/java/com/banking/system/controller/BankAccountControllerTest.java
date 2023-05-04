package com.banking.system.controller;

import com.bank.system.model.BankAccountRequest;
import com.bank.system.model.TransferModel;
import com.banking.system.exception.RestExceptionHandler;
import com.banking.system.exception.custom.NotFoundException;
import com.banking.system.service.impl.BankAccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.banking.system.fixture.AccountFixture.*;
import static com.banking.system.fixture.TransactionsFixture.transactionListResponse;
import static com.banking.system.fixture.TransferModelFixture.transferModel;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BankAccountControllerTest {

    private static String BASE_URL= "http://localhost/api";
    MockMvc mocMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Mock
    BankAccountServiceImpl bankAccountService;

    @InjectMocks
    BankAccountController bankAccountController;

    @BeforeEach
    public void init() {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mocMvc = MockMvcBuilders.standaloneSetup(bankAccountController)
                .setControllerAdvice(new RestExceptionHandler()).build();
    }
    @Test
    @DisplayName("calling POST/bank-account with correct body return 201 with valid headers")
    void WhenCreateBankAccount_ThenStatus201() throws Exception {
        String json = mapper.writeValueAsString(bankAccountRequest());
        when(bankAccountService.createBankAccount(isA(BankAccountRequest.class))).thenReturn(1234123L);
        mocMvc.perform(post(BASE_URL + "/bank-account").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(header().string("accountId", containsString( "1234123")))
                    .andExpect(header().string("Location", containsString( "http://localhost/api/bank-account/1234123")))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

    @Test
    @DisplayName("calling POST/bank-account with body contains null account status")
    void WhenCreateBankAccount_ThenStatus400_AccountTypeIsNull() throws Exception {
        String json = mapper.writeValueAsString(bankAccountRequestWithNullAccountType());
        System.out.println(json);
        mocMvc.perform(post(BASE_URL + "/bank-account").contentType(MediaType.APPLICATION_JSON).content(json))
                 .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("calling GET/bank-account/{accountId} with not found accountId")
    void WhenGetBankAccount_ThenStatus404_NotFoundAccountId() throws Exception {
        when(bankAccountService.getBankAccount(anyLong())).thenThrow(NotFoundException.class);
        mocMvc.perform(get(BASE_URL + "/bank-account/11111").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("calling GET/bank-account/{accountId} with success accountId")
    void WhenGetBankAccount_ThenStatus200_SuccessAccountId() throws Exception {
        when(bankAccountService.getBankAccount(anyLong())).thenReturn(bankAccountResponse());
        mocMvc.perform(get(BASE_URL + "/bank-account/1111").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("calling GET/bank-account/{accountId}/transactions with wrong account id")
    void WhenGetBankAccountTransactions_ThenStatus404_NotFoundAccountId() throws Exception {
        when(bankAccountService.getTransactions(anyLong())).thenThrow(NotFoundException.class);
        mocMvc.perform(get(BASE_URL + "/bank-account/11111/transactions").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("calling GET/bank-account/{accountId}/transactions with correct account id")
    void WhenGetBankAccountTransactions_ThenStatus200_correctAccountId() throws Exception {
        when(bankAccountService.getTransactions(anyLong())).thenReturn(transactionListResponse());
        mocMvc.perform(get(BASE_URL + "/bank-account/11111/transactions").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("calling POST/bank-account/transfer with correct request")
    void WhenTransferMoney_ThenStatus200_correctAccountId() throws Exception {
        String json = mapper.writeValueAsString(transferModel());
        doNothing().when(bankAccountService).transfer(isA(TransferModel.class));
        mocMvc.perform(post(BASE_URL + "/bank-account/transfer").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
