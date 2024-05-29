package com.vladyslavberezovskyi.dto.budget;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBudgetRequest {

    private String name;
    private Double currentValue;
    private Double neededValue;
    private UUID category;

}
