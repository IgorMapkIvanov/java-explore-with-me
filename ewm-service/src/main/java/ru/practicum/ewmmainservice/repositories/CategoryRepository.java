package ru.practicum.ewmmainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmainservice.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}