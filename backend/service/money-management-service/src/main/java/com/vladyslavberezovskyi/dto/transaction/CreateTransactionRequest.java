package com.vladyslavberezovskyi.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequest {

    private String name;
    private Double value;
    private UUID balance;
    private UUID credit;
    private Date date;
    private UUID category;

}
