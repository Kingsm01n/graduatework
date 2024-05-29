package com.vladyslavberezovskyi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Balance {

    private UUID id;
    private String name;
    private Double currentValue;
    private UUID userId;
    private List<UUID> transactions;

}
