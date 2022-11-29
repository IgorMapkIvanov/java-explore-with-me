package ru.practicum.ewmmainservice.services.categories;

import ru.practicum.ewmmainservice.dto.categories.CategoryDto;
import ru.practicum.ewmmainservice.dto.categories.NewCategoryDto;

public interface CategoryAdminService {
    CategoryDto postCategory(NewCategoryDto dto);

    CategoryDto updateCategory(CategoryDto dto);

    void deleteCategory(Long catId);
}