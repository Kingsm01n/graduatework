package com.vladyslavberezovskyi.mapper;

import com.vladyslavberezovskyi.util.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageMapper {

    public Pageable pageRequestToPageable(PageRequest pageRequest) {
        return org.springframework.data.domain.PageRequest.of(
                pageRequest.getPage(),
                pageRequest.getSize());
    }
}
