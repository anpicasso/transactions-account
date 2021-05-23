package com.pichincha.backend.test.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewTransactionDto {

    private Long accountId;

    private String type;

    private String comment;
}
