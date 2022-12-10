package ru.practicum.ewmmainservice.services.compilation;

import ru.practicum.ewmmainservice.dto.compilation.CompilationDto;

import java.util.List;

public interface CompilationPublicService {
    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long compId);
}