package com.vladyslavberezovskyi.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor//TODO
public class CategoryResponse {

    private UUID id;
    private String name;

}
