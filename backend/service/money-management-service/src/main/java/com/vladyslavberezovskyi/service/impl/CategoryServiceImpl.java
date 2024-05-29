package com.vladyslavberezovskyi.service.impl;

import com.vladyslavberezovskyi.dao.entity.CategoryEntity;
import com.vladyslavberezovskyi.dao.repository.CategoryRepository;
import com.vladyslavberezovskyi.error.ResourceNotFoundException;
import com.vladyslavberezovskyi.mapper.CategoryMapper;
import com.vladyslavberezovskyi.model.Category;
import com.vladyslavberezovskyi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor//TODO add userId to queries
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public Category getCategoryById(UUID categoryId) {
        return mapper.entityToModel(repository.findById(categoryId)
                .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public List<Category> getAllCategories() {
        return mapper.entityToModel(repository.findAll());
    }

    @Override
    public Page<Category> getAllCategories(Pageable pageable) {
        return mapper.entityToModel(repository.findAll(pageable));
    }

    @Override
    public Category createCategory(Category category) {
        CategoryEntity categoryEntity = mapper.modelToEntity(category);
        categoryEntity = repository.saveAndFlush(categoryEntity);

        return mapper.entityToModel(categoryEntity);
    }

    @Override
    public Category updateCategory(UUID categoryId, Category category) {
        CategoryEntity categoryEntity = repository.findById(categoryId)
                .orElseThrow(ResourceNotFoundException::new);
        mapper.update(categoryEntity, category);
        categoryEntity = repository.saveAndFlush(categoryEntity);

        return mapper.entityToModel(categoryEntity);
    }
}
