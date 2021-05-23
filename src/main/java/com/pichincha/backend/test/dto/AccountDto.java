package com.pichincha.backend.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class AccountDto {

    private final String number;

    private final String type;

    private final LocalDateTime creationDate;

}
