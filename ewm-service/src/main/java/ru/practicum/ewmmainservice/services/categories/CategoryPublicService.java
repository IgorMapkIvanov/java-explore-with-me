package ru.practicum.ewmmainservice.services.categories;

import ru.practicum.ewmmainservice.dto.categories.CategoryDto;

import java.util.List;

public interface CategoryPublicService {
    CategoryDto getCategoryById(Long catId);

    List<CategoryDto> getCategories(int from, int size);

}