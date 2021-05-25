package com.pichincha.backend.test.service;

import com.pichincha.backend.test.dto.NewTransactionDto;
import com.pichincha.backend.test.dto.TransactionDto;
import com.pichincha.backend.test.exception.ResourceNotFoundException;
import com.pichincha.backend.test.model.Transaction;
import com.pichincha.backend.test.repository.AccountRepository;
import com.pichincha.backend.test.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Returns a list of all transactions for a account with passed id.
     *
     * @param accountId id of the account
     * @return list of transactions sorted by creation date descending - most recent first
     */
    public List<TransactionDto> getTransactionsForAccount(Long accountId) {
        log.info("Searching Transactions for Account ID: " + accountId);
        return transactionRepository.findByAccountId(accountId)
                .stream()
                .map(transaction -> new TransactionDto(transaction.getId(), transaction.getComment(), transaction.getType(), transaction.getCreationDate()))
                .collect(Collectors.toList());
    }

    /**
     * Creates a new transaction
     *
     * @param newTransactionDto data of new transaction
     * @return id of the created transaction
     * @throws IllegalArgumentException if there is no account for passed newTransactionDto.accountId
     */
    public Long addTransaction(NewTransactionDto newTransactionDto) {
        Long accountId = newTransactionDto.getAccountId();
        log.info("Adding Transaction to Account ID: " + accountId);

        return accountRepository.findById(accountId).map(account -> {
            var transaction = mapNewTransactionDtoToEntity(newTransactionDto);
            transaction.setAccount(account);
            return transactionRepository.save(transaction).getId();
        }).orElseThrow(() -> new ResourceNotFoundException("AccountId " + accountId + " not found"));
    }

    private Transaction mapNewTransactionDtoToEntity(NewTransactionDto newTransactionDto) {
        var transaction = new Transaction();
        transaction.setType(newTransactionDto.getType());
        transaction.setComment(newTransactionDto.getComment());
        transaction.setCreationDate(LocalDateTime.now());
        return transaction;
    }
}
