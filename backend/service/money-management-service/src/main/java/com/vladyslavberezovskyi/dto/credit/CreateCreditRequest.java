package com.vladyslavberezovskyi.dto.credit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor//TODO
public class CreateCreditRequest {

    private String name;

}
