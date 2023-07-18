package ru.practicum.ewmmainservice.controllers.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.categoriy.CategoryDto;
import ru.practicum.ewmmainservice.services.category.CategoryPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryPublicController {
    private final CategoryPublicService service;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(name = "from", required = false, defaultValue = "0")
                                           @PositiveOrZero Integer from,
                                           @RequestParam(name = "size", required = false, defaultValue = "10")
                                           @Positive Integer size) {
        log.info("CATEGORY_PUBLIC_CONTROLLER: Get categories.");
        return service.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable @Positive Long catId) {
        log.info("CATEGORY_PUBLIC_CONTROLLER: Get category by ID = {}.", catId);
        return service.getCategoryById(catId);
    }
}