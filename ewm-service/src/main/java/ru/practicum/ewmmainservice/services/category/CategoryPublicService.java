package ru.practicum.ewmmainservice.services.category;

import ru.practicum.ewmmainservice.dto.categoriy.CategoryDto;

import java.util.List;

public interface CategoryPublicService {
    CategoryDto getCategoryById(Long catId);

    List<CategoryDto> getCategories(Integer from, Integer size);
}