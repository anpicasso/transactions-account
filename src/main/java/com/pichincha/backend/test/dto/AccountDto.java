package com.pichincha.backend.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AccountDto {

    private final String number;

    private final String type;

    private final LocalDateTime creationDate;

}
