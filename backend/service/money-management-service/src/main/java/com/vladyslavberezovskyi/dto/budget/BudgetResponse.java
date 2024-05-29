package com.vladyslavberezovskyi.dto.budget;

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
public class BudgetResponse {

    private UUID id;
    private String name;
    private Double currentValue;
    private Double neededValue;
    private List<UUID> transactions;
    private UUID category;
}
