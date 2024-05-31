package com.vladyslavberezovskyi.mapper;

import com.vladyslavberezovskyi.dao.entity.CategoryEntity;
import com.vladyslavberezovskyi.dto.category.CategoryResponse;
import com.vladyslavberezovskyi.dto.category.CreateCategoryRequest;
import com.vladyslavberezovskyi.dto.category.PatchCategoryRequest;
import com.vladyslavberezovskyi.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)//TODO map ignores
public interface CategoryMapper {
    CategoryResponse modelToDto(Category category);

    default List<CategoryResponse> modelToDto(@Nullable List<Category> categories) {
        if (categories == null) {
            return null;
        }

        return categories.stream()
                .map(this::modelToDto)
                .toList();
    }

    default Page<CategoryResponse> modelToDto(@Nullable Page<Category> categories) {
        if (categories == null) {
            return null;
        }

        return categories.map(this::modelToDto);
    }

    Category dtoToModel(CreateCategoryRequest createCategoryRequest);

    Category dtoToModel(PatchCategoryRequest patchCategoryRequest);

    Category entityToModel(CategoryEntity categoryEntity);

    default List<Category> entityToModel(@Nullable List<CategoryEntity> categoryEntities) {
        if (categoryEntities == null) {
            return null;
        }

        return categoryEntities.stream()
                .map(this::entityToModel)
                .toList();
    }

    default Page<Category> entityToModel(@Nullable Page<CategoryEntity> categoryEntities) {
        if (categoryEntities == null) {
            return null;
        }

        return categoryEntities.map(this::entityToModel);
    }

    CategoryEntity modelToEntity(Category category);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget CategoryEntity categoryEntity, Category category);
}
