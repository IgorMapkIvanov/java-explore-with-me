package ru.practicum.ewmmainservice.services.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.dto.categories.CategoryDto;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.mappers.categories.CategoryMapper;
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
     * @param pageable  {@link Pageable}
     * @return {@link List} of {@link CategoryDto}
     */
    @Override
    public List<CategoryDto> getCategories(Pageable pageable) {
        List<CategoryDto> categoryDtos = categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toUnmodifiableList());
        log.info("CATEGORY_PUBLIC_SERVICE: Get categories.");
        return categoryDtos;
    }

    /**
     * Get category by id.
     *
     * @param catId  {@link Long}
     * @return {@link CategoryDto}
     */
    @Override
    public CategoryDto getCategoryById(Long catId) {
        log.info("CATEGORY_PUBLIC_SERVICE: Get category with ID = {}.", catId);
        return CategoryMapper.toCategoryDto(categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.
                        format("Category with id = '%s' not found.", catId), "Category not found")));
    }
}