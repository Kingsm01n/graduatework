package com.vladyslavberezovskyi.dto.balance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor//TODO add validation
public class CreateBalanceRequest {

    private String name;
    private Double currentValue;

}
