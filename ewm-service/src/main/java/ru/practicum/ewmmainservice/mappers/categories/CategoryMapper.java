package ru.practicum.ewmmainservice.mappers.categories;

import ru.practicum.ewmmainservice.dto.categories.CategoryDto;
import ru.practicum.ewmmainservice.models.Category;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static Category toCategory(CategoryDto dto) {
        return new Category(dto.getId(), dto.getName());
    }
}