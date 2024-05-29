package com.vladyslavberezovskyi.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageRequest {

    private int page;
    private int size;
    private String[] sort;

}
