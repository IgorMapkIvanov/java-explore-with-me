package ru.practicum.ewmmainservice.services.categories;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.dto.categories.CategoryDto;

import java.util.List;

public interface CategoryPublicService {
    CategoryDto getCategoryById(Long catId);

    List<CategoryDto> getCategories(Pageable pageable);
}