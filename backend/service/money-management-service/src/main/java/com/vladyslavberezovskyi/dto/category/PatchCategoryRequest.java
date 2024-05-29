package com.vladyslavberezovskyi.dto.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder//TODO
public class PatchCategoryRequest {

    private String name;
}
