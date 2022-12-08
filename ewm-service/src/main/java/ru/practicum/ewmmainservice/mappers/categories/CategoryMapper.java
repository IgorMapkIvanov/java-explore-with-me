package ru.practicum.ewmmainservice.mappers.categories;

import ru.practicum.ewmmainservice.dto.categories.CategoryDto;
import ru.practicum.ewmmainservice.dto.categories.NewCategoryDto;
import ru.practicum.ewmmainservice.models.Category;

public class CategoryMapper {
    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category toCategory(NewCategoryDto newCategoryDto) {
        return Category.builder()
                .id(0L)
                .name(newCategoryDto.getName())
                .build();
    }
}