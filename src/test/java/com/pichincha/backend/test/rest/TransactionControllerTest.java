package com.pichincha.backend.test.rest;

import com.pichincha.backend.test.dto.NewTransactionDto;
import com.pichincha.backend.test.dto.TransactionDto;
import com.pichincha.backend.test.exception.ResourceNotFoundException;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerTest extends AbstractControllerTest {


    @Test
    public void shouldReturnFoundTransactions() throws Exception {

        // given
        List<TransactionDto> transactions = new ArrayList<>();
        LocalDateTime creationDate = LocalDateTime.of(2018, 5, 20, 20, 51, 16);
        transactions.add(new TransactionDto(2L, "July payment", "Credit card payment", creationDate));

        // when
        when(transactionService.getTransactionsForAccount(1L)).thenReturn(transactions);

        // then
        mockMvc.perform(get("/accounts/1/transactions").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].comment", is("July payment")))
                .andExpect(jsonPath("$[0].type", is("Credit card payment")))
                .andExpect(jsonPath("$[0].creationDate", is(creationDate.toString())));

    }

    @Test
    public void shouldAddTransaction() throws Exception {

        // given
        String transactionBody = "{\"comment\":\"Test comment\", \"type\":\"Credit card payment\"}";
        NewTransactionDto newTransaction = new NewTransactionDto("Credit card payment", "Test comment");

        // when
        when(transactionService.addTransaction(newTransaction)).thenReturn(1L);

        // then
        mockMvc.perform(post("/accounts/1/transactions")
                .content(transactionBody)
                .contentType(APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldNotAddTransactionForNonExistingAccount() throws Exception {

        // given
        String transactionBody = "{\"comment\":\"Test comment\", \"type\":\"Credit card payment\"}";

        // when
        when(transactionService.addTransaction(any(NewTransactionDto.class))).thenThrow(new ResourceNotFoundException("AccountId " + 2 + " not found"));

        // then
        mockMvc.perform(post("/accounts/2/transactions")
                .content(transactionBody)
                .contentType(APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
