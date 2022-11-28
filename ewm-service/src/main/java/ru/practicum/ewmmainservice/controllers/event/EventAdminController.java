package ru.practicum.ewmmainservice.controllers.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.events.EventFullDto;
import ru.practicum.ewmmainservice.dto.events.UpdateEventRequest;
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
    public List<EventFullDto> getEvents(@RequestParam(name = "users", required = false) List<Integer> users,
                                        @RequestParam(name = "states", required = false) List<String> states,
                                        @RequestParam(name = "categories", required = false) List<Integer> categories,
                                        @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                        @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                        @RequestParam(name = "from", defaultValue = "0", required = false)
                                        @PositiveOrZero int from,
                                        @RequestParam(name = "size", defaultValue = "10", required = false)
                                        @Positive int size) {
        log.info("EVENT_ADMIN_CONTROLLER: Get events, params: " +
                        "users= {}, states= {}, categories= {}, rangeStart= {}, rangeEnd= {}, from= {}, size= {}.",
                users, states, categories, rangeStart, rangeEnd, from, size);
        return service.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable @Positive Long eventId,
                                    @RequestBody UpdateEventRequest updateEventRequest) {
        log.info("EVENT_ADMIN_CONTROLLER: Update event with ID = {}: {}.", eventId, updateEventRequest);
        return service.updateEvent(eventId, updateEventRequest);
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