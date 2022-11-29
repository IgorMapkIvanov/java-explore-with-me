package ru.practicum.ewmmainservice.controllers.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.categories.CategoryDto;
import ru.practicum.ewmmainservice.dto.categories.NewCategoryDto;
import ru.practicum.ewmmainservice.services.categories.CategoryAdminService;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/categories")
public class CategoryAdminController {
    private final CategoryAdminService service;

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) {
        log.info("CATEGORY_ADMIN_CONTROLLER: Update category: {}", categoryDto);
        return service.updateCategory(categoryDto);
    }

    @PostMapping
    public CategoryDto postCategory(@RequestBody NewCategoryDto categoryDto) {
        log.info("CATEGORY_ADMIN_CONTROLLER: Post category: {}", categoryDto);
        return service.postCategory(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        log.info("CATEGORY_ADMIN_CONTROLLER: Delete category: {}", catId);
        service.deleteCategory(catId);
    }
}