package com.pichincha.backend.test.service;


import com.pichincha.backend.test.dto.AccountDto;
import com.pichincha.backend.test.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDto getAccount(Long id) {
        log.info("Searching Account");
        return accountRepository.findById(id)
                .map(account -> new AccountDto(account.getNumber(), account.getType(), account.getCreationDate()))
                .orElse(null);
    }

}
