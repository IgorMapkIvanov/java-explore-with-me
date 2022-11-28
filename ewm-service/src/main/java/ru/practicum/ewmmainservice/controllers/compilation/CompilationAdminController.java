package ru.practicum.ewmmainservice.controllers.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.compilations.CompilationDto;
import ru.practicum.ewmmainservice.dto.compilations.NewCompilationDto;
import ru.practicum.ewmmainservice.services.compilations.CompilationAdminService;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
public class CompilationAdminController {
    private final CompilationAdminService service;

    @PostMapping("/admin/compilations")
    public CompilationDto addCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        log.info("COMPILATION_ADMIN_CONTROLLER: Add compilation: {}.", newCompilationDto);
        return service.addCompilation(newCompilationDto);
    }

    @PatchMapping("/admin/compilations/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable Long compId, @PathVariable Long eventId) {
        log.info("COMPILATION_ADMIN_CONTROLLER: Add event with ID = {} to compilation with ID = {}.", eventId, compId);
        service.addEventToCompilation(compId, eventId);
    }

    @PatchMapping("/admin/compilations/{compId}/pin")
    public void pinCompilationToHomepage(@PathVariable Long compId) {
        log.info("COMPILATION_ADMIN_CONTROLLER: Pin compilation with ID = {} to homepage.", compId);
        service.pinCompilationToHomepage(compId);
    }

    @DeleteMapping("/admin/compilations/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable Long compId, @PathVariable Long eventId) {
        log.info("COMPILATION_ADMIN_CONTROLLER: Delete event with ID = {} from compilation with ID = {}.", eventId, compId);
        service.deleteEventFromCompilation(compId, eventId);
    }

    @DeleteMapping("/admin/compilations/{compId}/pin")
    public void unpinCompilation(@PathVariable Long compId) {
        log.info("COMPILATION_ADMIN_CONTROLLER: Unpin compilation with ID = {} from homepage.", compId);
        service.unpinCompilation(compId);
    }

    @DeleteMapping("/admin/compilations/{compId}")
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("COMPILATION_ADMIN_CONTROLLER: Delete compilation with ID = {}.", compId);
        service.deleteCompilation(compId);
    }
}