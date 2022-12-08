package ru.practicum.ewmmainservice.services.category;

import ru.practicum.ewmmainservice.dto.category.CategoryDto;
import ru.practicum.ewmmainservice.dto.category.NewCategoryDto;

public interface CategoryAdminService {
    CategoryDto postCategory(NewCategoryDto dto);

    CategoryDto updateCategory(CategoryDto dto);

    void deleteCategory(Long catId);
}