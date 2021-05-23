package com.pichincha.backend.test.rest;

import com.pichincha.backend.test.dto.NewTransactionDto;
import com.pichincha.backend.test.dto.TransactionDto;
import com.pichincha.backend.test.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/transactions")
public class TransactionController {

    private final AccountService aService;

    public TransactionController(AccountService accountService) {
        this.aService = accountService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDto> getTransactionsForAccount(@PathVariable Long accountId) {
        return aService.getTransactionsForAccount(accountId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long addTransaction(@PathVariable Long accountId, @RequestBody NewTransactionDto newTransactionDto) {
        newTransactionDto.setAccountId(accountId);
        return aService.addTransaction(newTransactionDto);
    }

}
