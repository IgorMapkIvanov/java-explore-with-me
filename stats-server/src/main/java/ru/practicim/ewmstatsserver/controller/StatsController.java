package ru.practicim.ewmstatsserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import ru.practicim.ewmstatsserver.dto.EndpointHitDto;
import ru.practicim.ewmstatsserver.exceptions.DateTimeValidationException;
import ru.practicim.ewmstatsserver.model.ViewStats;
import ru.practicim.ewmstatsserver.service.StatsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.ALL_VALUE)
@Validated
public class StatsController {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final StatsService statsService;

    @GetMapping(path = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<ViewStats>> getStats(
            @RequestParam(value = "start") String start,
            @RequestParam(value = "end") String end,
            @RequestParam(value = "uris", required = false) List<String> uris,
            @RequestParam(value = "unique", required = false, defaultValue = "false") Boolean unique) {
        return statsService.getStats(
                        getLocalDateTime(start),
                        getLocalDateTime(end),
                        uris,
                        unique)
                .log("Controller -> getStats",
                        Level.INFO,
                        SignalType.ON_SUBSCRIBE, SignalType.ON_COMPLETE, SignalType.ON_NEXT);
    }

    private LocalDateTime getLocalDateTime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeValidationException("Value: " + dateTime + ", can't be parsed to LocalDateTime");
        }
    }

    @PostMapping(path = "/hit", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<EndpointHitDto> addEndpoint(@RequestBody EndpointHitDto endpointHitDto) {
        return statsService.addEndpoint(endpointHitDto)
                .log("Controller -> addEndpoint",
                        Level.INFO,
                        SignalType.ON_SUBSCRIBE, SignalType.ON_COMPLETE, SignalType.ON_NEXT);
    }
}