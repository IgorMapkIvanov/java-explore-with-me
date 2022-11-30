package ru.practicum.ewmmainservice.services.compilations;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.dto.compilations.CompilationDto;

import java.util.List;

public interface CompilationPublicService {
    List<CompilationDto> getCompilations(Boolean pinned, Pageable pageable);

    CompilationDto getCompilationById(Long compId);
}