package com.pichincha.backend.test.service;

import com.pichincha.backend.test.dto.NewTransactionDto;
import com.pichincha.backend.test.dto.TransactionDto;
import com.pichincha.backend.test.model.Account;
import com.pichincha.backend.test.model.Transaction;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TransactionServiceTest extends AbstractServiceTest {

    private Account createTestAccount() {
        Account account = new Account();
        account.setNumber("Test Number");
        account.setType("Test type");
        LocalDateTime creationDate = LocalDateTime.of(2018, 5, 20, 20, 51, 16);
        account.setCreationDate(creationDate);
        accountRepository.save(account);
        return account;
    }

    @Test
    public void shouldAddTransaction() {
        Account account = createTestAccount();

        NewTransactionDto transaction = new NewTransactionDto("Type", "Comment");
        transaction.setAccountId(account.getId());

        Long transactionId = transactionService.addTransaction(transaction);

        assertThat("Transaction id shouldn't be null", transactionId, notNullValue());
    }

    @Test
    public void shouldReturnAddedTransaction() {
        Account account = createTestAccount();

        NewTransactionDto transaction = new NewTransactionDto("Type", "Comment");
        transaction.setAccountId(account.getId());

        transactionService.addTransaction(transaction);

        List<TransactionDto> transactions = transactionService.getTransactionsForAccount(account.getId());

        assertThat("There should be one transaction", transactions, hasSize(1));
        assertThat(transactions.get(0).getType(), Matchers.equalTo("Type"));
        assertThat(transactions.get(0).getComment(), Matchers.equalTo("Comment"));
    }

    @Test
    public void shouldReturnSavedTransaction() {
        Account account = createTestAccount();

        Transaction transaction = new Transaction();
        transaction.setType("Type");
        transaction.setComment("Comment");
        LocalDateTime creationDate = LocalDateTime.of(2018, 5, 20, 20, 51, 16);
        transaction.setCreationDate(creationDate);
        transaction.setAccountId(account.getId());

        transactionRepository.save(transaction);

        List<TransactionDto> transactions = transactionService.getTransactionsForAccount(account.getId());

        assertThat("There should be one transaction", transactions, hasSize(1));
        assertThat(transactions.get(0).getType(), Matchers.equalTo("Type"));
        assertThat(transactions.get(0).getComment(), Matchers.equalTo("Comment"));
        assertThat(transactions.get(0).getCreationDate(), equalTo(creationDate));
    }

    @Test
    public void shouldReturnEmptyListForAccountWithNoTransactions() {
        Account account = createTestAccount();
        List<TransactionDto> transactions = transactionService.getTransactionsForAccount(account.getId());

        assertThat("There shouldn't be any transactions", transactions, empty());
    }


}
