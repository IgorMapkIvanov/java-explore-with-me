package ru.practicum.ewmmainservice.services.compilations;

import ru.practicum.ewmmainservice.dto.compilations.CompilationDto;

import java.util.List;

public interface CompilationPublicService {
    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long compId);
}