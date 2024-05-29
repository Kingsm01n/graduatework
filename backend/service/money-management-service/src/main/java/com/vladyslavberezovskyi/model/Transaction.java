package com.vladyslavberezovskyi.model;

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
public class Transaction {

    private UUID id;
    private String name;
    private Double value;
    private UUID balance;
    private UUID credit;
    private UUID userId;
    private Date date;
    private UUID category;

}
