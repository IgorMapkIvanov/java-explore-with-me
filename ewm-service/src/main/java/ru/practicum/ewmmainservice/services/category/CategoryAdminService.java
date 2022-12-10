package ru.practicum.ewmmainservice.services.category;

import ru.practicum.ewmmainservice.dto.categoriy.CategoryDto;
import ru.practicum.ewmmainservice.dto.categoriy.NewCategoryDto;

public interface CategoryAdminService {
    CategoryDto postCategory(NewCategoryDto dto);

    CategoryDto updateCategory(CategoryDto dto);

    void deleteCategory(Long catId);
}