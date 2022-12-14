package ru.practicum.ewmmainservice.controllers.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.compilation.CompilationDto;
import ru.practicum.ewmmainservice.services.compilation.CompilationPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/compilations")
public class CompilationPublicController {
    private final CompilationPublicService service;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                                @RequestParam(name = "from", defaultValue = "0")
                                                @PositiveOrZero Integer from,
                                                @RequestParam(name = "size", defaultValue = "10")
                                                @Positive Integer size) {
        log.info("COMPILATION_PUBLIC_CONTROLLER: Get compilations with param: pinned = {}, from = {}, size = {}.",
                pinned, from, size);
        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable @Positive Long compId) {
        log.info("COMPILATION_PUBLIC_CONTROLLER: Get compilation with ID = {}.", compId);
        return service.getCompilationById(compId);
    }
}