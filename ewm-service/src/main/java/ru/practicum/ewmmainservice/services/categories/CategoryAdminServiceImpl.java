package ru.practicum.ewmmainservice.services.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.dto.categories.CategoryDto;
import ru.practicum.ewmmainservice.dto.categories.NewCategoryDto;
import ru.practicum.ewmmainservice.exceptions.ConflictException;
import ru.practicum.ewmmainservice.exceptions.DeleteCategoryException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.mappers.categories.CategoryMapper;
import ru.practicum.ewmmainservice.models.Category;
import ru.practicum.ewmmainservice.repositories.CategoryRepository;
import ru.practicum.ewmmainservice.repositories.EventRepository;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryAdminServiceImpl implements CategoryAdminService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    /**
     * Update category.
     *
     * @param categoryDto {@link CategoryDto}
     * @return {@link CategoryDto}
     */
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        log.info("CATEGORY_ADMIN_SERVICE: Update category with ID = {}.", categoryDto.getId());
        Category category = checkCategoryInDbAndReturn(categoryDto.getId());
        if (categoryDto.getName() != null && !categoryDto.getName().isEmpty()) {
            category.setName(categoryDto.getName());
        }
        return CategoryMapper.toDto(category);
    }

    /**
     * Add new category.
     *
     * @param newCategoryDto {@link NewCategoryDto}
     * @return {@link CategoryDto}
     */
    @Override
    public CategoryDto postCategory(NewCategoryDto newCategoryDto) {
        try {
            Category category = categoryRepository.save(CategoryMapper.toCategory(newCategoryDto));
            log.info("CATEGORY_ADMIN_SERVICE: Add new category: ID = {}.", category.getId());
            return CategoryMapper.toDto(category);
        } catch (DataIntegrityViolationException e) {
            String message = e.getMessage();
            String reason = "Conflict exception";
            throw new ConflictException(message, reason);
        }
    }

    /**
     * Delete category.
     *
     * @param catId {@link Long}
     */
    @Override
    public void deleteCategory(Long catId) {
        checkCategoryInDbAndReturn(catId);
        checkEventWithCategory(catId);
        log.info("CATEGORY_ADMIN_SERVICE: Delete category with ID = {}.", catId);
        categoryRepository.deleteById(catId);
    }

    /**
     * Check event with category in database.
     *
     * @param catId {@link Long}
     */
    private void checkEventWithCategory(Long catId) {
        if (!eventRepository.findByCategoryId(catId).isEmpty()) {
            String message = String.format("Event in db belongs to category with id = '%s'", catId);
            String reason = "Category is used in the event";
            throw new DeleteCategoryException(message, reason);
        }
    }

    /**
     * Check category in database.
     *
     * @param id {@link Long}
     */
    private Category checkCategoryInDbAndReturn(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Category with id = '%s', not found", id);
            String reason = "Category not found";
            throw new NotFoundException(message, reason);
        });
    }
}