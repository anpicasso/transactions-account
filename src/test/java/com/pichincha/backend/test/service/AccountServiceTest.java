package com.pichincha.backend.test.service;

import com.pichincha.backend.test.dto.AccountDto;
import com.pichincha.backend.test.dto.NewTransactionDto;
import com.pichincha.backend.test.dto.TransactionDto;
import com.pichincha.backend.test.model.Account;
import com.pichincha.backend.test.repository.AccountRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Test
    public void shouldReturnCreatedAccount() {
        Account account = new Account();
        account.setNumber("Test number");
        account.setType("Test type");
        LocalDateTime creationDate = LocalDateTime.of(2018, 5, 20, 20, 51, 16);
        account.setCreationDate(creationDate);
        accountRepository.save(account);

        AccountDto accountDto = accountService.getAccount(account.getId());

        assertNotNull("Account shouldn't be null", accountDto);
        assertThat(accountDto.getType(), equalTo("Test type"));
        assertThat(accountDto.getNumber(), equalTo("Test number"));
        assertThat(accountDto.getCreationDate(), equalTo(creationDate));
    }

    @Test
    public void shouldReturnNullForNotExistingAccount() {
        AccountDto accountDto = accountService.getAccount(123L);

        assertNull(accountDto);
    }


    @Test
    public void shouldAddTransaction() {
        Account account = createTestAccount();

        NewTransactionDto transaction = new NewTransactionDto();
        transaction.setAccountId(account.getId());
        transaction.setType("Type");
        transaction.setComment("Comment");
        Long transactionId = accountService.addTransaction(transaction);

        assertThat("Transaction id shouldn't be null", transactionId, notNullValue());
    }

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
    public void shouldReturnAddedTransaction() {
        Account account = createTestAccount();

        NewTransactionDto transaction = new NewTransactionDto();
        transaction.setAccountId(account.getId());
        transaction.setType("Type");
        transaction.setComment("Comment");

        accountService.addTransaction(transaction);

        List<TransactionDto> transactions = accountService.getTransactionsForAccount(account.getId());

        assertThat("There should be one transaction", transactions, hasSize(1));
        assertThat(transactions.get(0).getType(), Matchers.equalTo("Type"));
        assertThat(transactions.get(0).getComment(), Matchers.equalTo("Comment"));
    }
}
