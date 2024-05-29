package com.vladyslavberezovskyi.service;

import com.vladyslavberezovskyi.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    Category getCategoryById(UUID categoryId);

    List<Category> getAllCategories();

    Page<Category> getAllCategories(Pageable pageable);

    Category createCategory(Category category);

    Category updateCategory(UUID categoryId, Category category);

}
