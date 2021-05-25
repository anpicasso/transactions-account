package com.pichincha.backend.test.rest;

import com.pichincha.backend.test.dto.NewTransactionDto;
import com.pichincha.backend.test.dto.TransactionDto;
import com.pichincha.backend.test.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/accounts/{accountId}/transactions")
public class TransactionController {

    private final TransactionService tService;

    public TransactionController(TransactionService transactionService) {
        this.tService = transactionService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDto> getTransactionsForAccount(@PathVariable Long accountId) {
        return tService.getTransactionsForAccount(accountId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long addTransaction(@PathVariable Long accountId, @RequestBody NewTransactionDto newTransactionDto) {
        if (!accountId.equals(newTransactionDto.getAccountId())) {
            log.warn("Account ID ingresado en Path es distinto al ingresado en el Body, se usará el del Path.");
            newTransactionDto.setAccountId(accountId);
        }
        return tService.addTransaction(newTransactionDto);
    }

}
