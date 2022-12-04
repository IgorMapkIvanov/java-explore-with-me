package ru.practicum.ewmmainservice.controllers.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.events.EventFullDto;
import ru.practicum.ewmmainservice.dto.events.NewEventDto;
import ru.practicum.ewmmainservice.pageable.EwmPageable;
import ru.practicum.ewmmainservice.services.events.EventAdminService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/events")
public class EventAdminController {
    private final EventAdminService service;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(name = "users", required = false) List<Long> users,
                                        @RequestParam(name = "states", required = false) List<String> states,
                                        @RequestParam(name = "categories", required = false) List<Long> categories,
                                        @RequestParam(name = "rangeStart", required = false, defaultValue = "")
                                        String rangeStart,
                                        @RequestParam(name = "rangeEnd", required = false, defaultValue = "")
                                        String rangeEnd,
                                        @RequestParam(name = "from", defaultValue = "0", required = false)
                                        @PositiveOrZero int from,
                                        @RequestParam(name = "size", defaultValue = "10", required = false)
                                        @Positive int size) {
        log.info("EVENT_ADMIN_CONTROLLER: Get events, params: " +
                        "users= {}, states= {}, categories= {}, rangeStart= {}, rangeEnd= {}, from= {}, size= {}.",
                users, states, categories, rangeStart, rangeEnd, from, size);
        return service.getEvents(users, states, categories, rangeStart, rangeEnd,
                EwmPageable.of(from, size, Sort.by(Sort.Direction.ASC)));
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable @Positive Long eventId,
                                    @RequestBody NewEventDto newEventDto) {
        log.info("EVENT_ADMIN_CONTROLLER: Update event with ID = {}: {}.", eventId, newEventDto);
        return service.updateEvent(eventId, newEventDto);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable @Positive Long eventId) {
        log.info("EVENT_ADMIN_CONTROLLER: Publish event with ID = {}.", eventId);
        return service.publishEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable @Positive Long eventId) {
        log.info("EVENT_ADMIN_CONTROLLER: Reject event with ID = {}.", eventId);
        return service.rejectEvent(eventId);
    }
}