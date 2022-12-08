package ru.practicum.ewmmainservice.services.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.dto.category.CategoryDto;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.mappers.category.CategoryMapper;
import ru.practicum.ewmmainservice.pageable.EwmPageable;
import ru.practicum.ewmmainservice.repositories.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryPublicServiceImpl implements CategoryPublicService {
    private final CategoryRepository categoryRepository;

    /**
     * Get categories.
     *
     * @param from {@link Integer}
     * @param size {@link Integer}
     * @return {@link List} of {@link CategoryDto}
     */
    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = EwmPageable.of(from, size, Sort.by(Sort.Direction.ASC, "id"));
        List<CategoryDto> categoryDtos = categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
        log.info("CATEGORY_PUBLIC_SERVICE: Get categories.");
        return categoryDtos;
    }

    /**
     * Get category by id.
     *
     * @param catId {@link Long}
     * @return {@link CategoryDto}
     */
    @Override
    public CategoryDto getCategoryById(Long catId) {
        log.info("CATEGORY_PUBLIC_SERVICE: Get category with ID = {}.", catId);
        return CategoryMapper.toDto(categoryRepository.findById(catId)
                .orElseThrow(() -> {
                    String message = String.format("Category with id = '%s' not found.", catId);
                    String reason = "Category not found";
                    throw new NotFoundException(message, reason);
                }));
    }
}