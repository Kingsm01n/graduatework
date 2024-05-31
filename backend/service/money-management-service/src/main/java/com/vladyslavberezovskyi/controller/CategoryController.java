package com.vladyslavberezovskyi.controller;

import com.vladyslavberezovskyi.dto.category.CategoryResponse;
import com.vladyslavberezovskyi.dto.category.CreateCategoryRequest;
import com.vladyslavberezovskyi.dto.category.PatchCategoryRequest;
import com.vladyslavberezovskyi.mapper.CategoryMapper;
import com.vladyslavberezovskyi.mapper.PageMapper;
import com.vladyslavberezovskyi.security.annotations.IsUser;
import com.vladyslavberezovskyi.service.CategoryService;
import com.vladyslavberezovskyi.util.PageRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@SecurityRequirement(name = "Authorization")
public class CategoryController {

    private final CategoryService service;
    private final CategoryMapper mapper;
    private final PageMapper pageMapper;

    @IsUser
    @GetMapping("/{categoryId}")
    public CategoryResponse getCategoryById(@PathVariable UUID categoryId) {
        return mapper.modelToDto(service.getCategoryById(categoryId));
    }

    @IsUser
    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return mapper.modelToDto(service.getAllCategories());
    }

    @IsUser
    @GetMapping("/paged")
    public Page<CategoryResponse> getAllCategories(PageRequest pageRequest) {
        return mapper.modelToDto(service.getAllCategories(pageMapper.pageRequestToPageable(pageRequest)));
    }

    @IsUser
    @PostMapping
    public CategoryResponse createCategory(@RequestBody CreateCategoryRequest createCategoryRequest) {
        return mapper.modelToDto(service.createCategory(mapper.dtoToModel(createCategoryRequest)));
    }

    @IsUser
    @PatchMapping("/{categoryId}")
    public CategoryResponse updateCategory(@PathVariable UUID categoryId,
                                           @RequestBody PatchCategoryRequest patchCategoryRequest) {
        return mapper.modelToDto(service.updateCategory(categoryId, mapper.dtoToModel(patchCategoryRequest)));
    }

    @IsUser
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable UUID categoryId) {
        service.deleteCategory(categoryId);
    }

}
