package com.pichincha.backend.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TransactionDto {

    private final Long id;

    private final String comment;

    private final String type;

    private final LocalDateTime creationDate;

}
