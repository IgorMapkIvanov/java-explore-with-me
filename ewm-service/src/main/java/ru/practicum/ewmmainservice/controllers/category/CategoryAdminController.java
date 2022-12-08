package ru.practicum.ewmmainservice.controllers.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.categoriy.CategoryDto;
import ru.practicum.ewmmainservice.dto.categoriy.NewCategoryDto;
import ru.practicum.ewmmainservice.services.category.CategoryAdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/categories")
public class CategoryAdminController {
    private final CategoryAdminService service;

    @PostMapping
    public CategoryDto postCategory(@Valid @RequestBody NewCategoryDto categoryDto) {
        log.info("CATEGORY_ADMIN_CONTROLLER: Post category: {}", categoryDto);
        return service.postCategory(categoryDto);
    }

    @PatchMapping
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("CATEGORY_ADMIN_CONTROLLER: Update category: {}", categoryDto);
        return service.updateCategory(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable @Positive Long catId) {
        log.info("CATEGORY_ADMIN_CONTROLLER: Delete category: {}", catId);
        service.deleteCategory(catId);
    }
}