package ru.practicum.ewmmainservice.services.compilation;

import ru.practicum.ewmmainservice.dto.compilation.CompilationDto;
import ru.practicum.ewmmainservice.dto.compilation.NewCompilationDto;

public interface CompilationAdminService {
    CompilationDto addCompilation(NewCompilationDto dto);


    void addEventToCompilation(Long compId, Long eventId);

    void pinCompilationToHomepage(Long compId);

    void unpinCompilation(Long compId);


    void deleteEventFromCompilation(Long compId, Long eventId);

    void deleteCompilation(Long compId);
}