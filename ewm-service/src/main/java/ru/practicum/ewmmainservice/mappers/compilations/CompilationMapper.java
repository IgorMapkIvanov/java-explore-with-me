package ru.practicum.ewmmainservice.mappers.compilations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.dto.compilations.CompilationDto;
import ru.practicum.ewmmainservice.dto.compilations.NewCompilationDto;
import ru.practicum.ewmmainservice.mappers.events.EventShortMapper;
import ru.practicum.ewmmainservice.models.Compilation;
import ru.practicum.ewmmainservice.models.Event;
import ru.practicum.ewmmainservice.services.events.EventPublicService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Lazy
@Component
@RequiredArgsConstructor
public class CompilationMapper {
    private final EventPublicService service;
    @Lazy
    private final EventShortMapper eventShortMapper;

    public CompilationDto toCompilationDto(Compilation compilation) {
        List<Event> events = new ArrayList<>(compilation.getEvents());
        return new CompilationDto(compilation.getId(),
                compilation.getTitle(),
                compilation.getPinned(),
                events.stream()
                        .map(eventShortMapper::toEventShortDto)
                        .collect(Collectors.toList()));
    }

    public Compilation toCompilationFromNew(NewCompilationDto dto) {
        return new Compilation(0L,
                dto.getTitle(),
                dto.getPinned(),
                dto.getEvents().stream()
                        .map(service::getById)
                        .collect(Collectors.toSet()));
    }
}