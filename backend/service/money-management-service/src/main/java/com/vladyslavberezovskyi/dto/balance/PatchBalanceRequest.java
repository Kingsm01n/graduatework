package com.vladyslavberezovskyi.dto.balance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor//TODO make objects nullable
        //TODO add validation
public class PatchBalanceRequest {

    private String name;
    private Double currentValue;

}
