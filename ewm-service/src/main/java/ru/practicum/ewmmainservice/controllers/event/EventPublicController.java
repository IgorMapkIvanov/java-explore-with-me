package ru.practicum.ewmmainservice.controllers.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.clients.StatsClient;
import ru.practicum.ewmmainservice.dto.events.EventFullDto;
import ru.practicum.ewmmainservice.dto.events.EventShortDto;
import ru.practicum.ewmmainservice.dto.statistics.EndpointHit;
import ru.practicum.ewmmainservice.enums.Sort;
import ru.practicum.ewmmainservice.exceptions.SortInvalidException;
import ru.practicum.ewmmainservice.services.events.EventPublicService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/events")
public class EventPublicController {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String app = "service";
    private final EventPublicService service;
    private final StatsClient statsClient;

    @GetMapping(value = "/{id}")
    public EventFullDto getEventById(@PathVariable @Positive Long id, HttpServletRequest request) {
        EndpointHit endpointHit = new EndpointHit(0L,
                request.getRequestURI(),
                app,
                request.getRemoteAddr(),
                LocalDateTime.now().format(formatter));
        log.info("EVENT_PUBLIC_CONTROLLER: Send to stats server: {}", endpointHit);
        statsClient.createHit(endpointHit);

        log.info("EVENT_PUBLIC_CONTROLLER: Get event by ID = {}.", id);
        return service.getEventById(id);
    }

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(name = "text", required = false) String text,
                                         @RequestParam(name = "categories", required = false) List<Long> categories,
                                         @RequestParam(name = "paid", required = false) Boolean paid,
                                         @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                         @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                         @RequestParam(name = "onlyAvailable", defaultValue = "false") boolean onlyAvailable,
                                         @RequestParam(name = "sort", required = false) String sort,
                                         @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                         @RequestParam(name = "size", defaultValue = "10") @Positive int size,
                                         HttpServletRequest request) {
        Converter<String, Sort> converter = source -> {
            try {
                return Sort.valueOf(source.trim().toUpperCase(Locale.ROOT));
            } catch (Exception e) {
                return Sort.UNSUPPORTED_SORT;
            }
        };

        Sort sortInRequest = Sort.NO_SORT;
        if (sort != null && !sort.isBlank() && !sort.isEmpty()) {
            sortInRequest = converter.convert(sort);
            if (sortInRequest == Sort.UNSUPPORTED_SORT) {
                throw new SortInvalidException();
            }
        }

        EndpointHit endpointHit = new EndpointHit(0L,
                request.getRequestURI(),
                app,
                request.getRemoteAddr(),
                LocalDateTime.now().format(formatter));
        log.info("EVENT_PUBLIC_CONTROLLER: Send to stats server: {}", endpointHit);
        statsClient.createHit(endpointHit);

        log.info("EVENT_PUBLIC_CONTROLLER: Get events with params.");
        return service.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sortInRequest, from, size);
    }
}