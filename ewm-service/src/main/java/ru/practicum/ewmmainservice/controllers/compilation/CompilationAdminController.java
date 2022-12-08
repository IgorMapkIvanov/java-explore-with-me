package ru.practicum.ewmmainservice.controllers.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.compilation.CompilationDto;
import ru.practicum.ewmmainservice.dto.compilation.NewCompilationDto;
import ru.practicum.ewmmainservice.services.compilation.CompilationAdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/compilations")
public class CompilationAdminController {
    private final CompilationAdminService service;

    @PostMapping()
    public CompilationDto addCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("COMPILATION_ADMIN_CONTROLLER: Add compilation: {}.", newCompilationDto);
        return service.addCompilation(newCompilationDto);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable @Positive Long compId, @PathVariable @Positive Long eventId) {
        log.info("COMPILATION_ADMIN_CONTROLLER: Add event with ID = {} to compilation with ID = {}.", eventId, compId);
        service.addEventToCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilationToHomepage(@PathVariable @Positive Long compId) {
        log.info("COMPILATION_ADMIN_CONTROLLER: Pin compilation with ID = {} to homepage.", compId);
        service.pinCompilationToHomepage(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable @Positive Long compId, @PathVariable @Positive Long eventId) {
        log.info("COMPILATION_ADMIN_CONTROLLER: Delete event with ID = {} from compilation with ID = {}.",
                eventId, compId);
        service.deleteEventFromCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable @Positive Long compId) {
        log.info("COMPILATION_ADMIN_CONTROLLER: Unpin compilation with ID = {} from homepage.", compId);
        service.unpinCompilation(compId);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable @Positive Long compId) {
        log.info("COMPILATION_ADMIN_CONTROLLER: Delete compilation with ID = {}.", compId);
        service.deleteCompilation(compId);
    }
}