package ru.practicum.ewmmainservice.mappers.compilations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.dto.compilations.CompilationDto;
import ru.practicum.ewmmainservice.dto.compilations.NewCompilationDto;
import ru.practicum.ewmmainservice.mappers.events.EventShortMapper;
import ru.practicum.ewmmainservice.models.Compilation;
import ru.practicum.ewmmainservice.services.events.EventPublicService;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {
    private final EventPublicService eventPublicService;
    private final EventShortMapper eventShortMapper;

    public CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(compilation.getId(),
                compilation.getTitle(),
                compilation.getPinned(),
                compilation.getEvents().stream()
                        .map(eventShortMapper::toEventShortDto)
                        .collect(Collectors.toUnmodifiableList()));
    }

    public Compilation toCompilationFromNew(NewCompilationDto newCompilationDto) {
        return new Compilation(0L,
                newCompilationDto.getTitle(),
                newCompilationDto.getPinned(),
                newCompilationDto.getEvents().stream()
                        .map(eventPublicService::getById)
                        .collect(Collectors.toUnmodifiableList()));
    }
}