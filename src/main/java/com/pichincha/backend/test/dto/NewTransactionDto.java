package com.pichincha.backend.test.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class NewTransactionDto {

    private final String type;
    
    private final String comment;

    @Setter
    private Long accountId;

    public NewTransactionDto(String type, String comment) {
        this.type = type;
        this.comment = comment;
    }

}
