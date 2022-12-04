package ru.practicum.ewmmainservice.controllers.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.events.EventFullDto;
import ru.practicum.ewmmainservice.dto.events.EventShortDto;
import ru.practicum.ewmmainservice.dto.events.NewEventDto;
import ru.practicum.ewmmainservice.dto.events.UpdateEventDto;
import ru.practicum.ewmmainservice.dto.requests.ParticipationRequestDto;
import ru.practicum.ewmmainservice.pageable.EwmPageable;
import ru.practicum.ewmmainservice.services.events.EventPrivateService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}/events")
public class EventPrivateController {
    private final EventPrivateService eventPrivateService;

    @GetMapping
    public List<EventShortDto> getEventsOfUser(@PathVariable @Positive Long userId,
                                               @RequestParam(name = "from", defaultValue = "0", required = false)
                                               @PositiveOrZero int from,
                                               @RequestParam(name = "size", defaultValue = "10", required = false)
                                               @Positive int size) {
        Pageable pageable = EwmPageable.of(from, size, Sort.by(Sort.Direction.ASC, "id"));
        log.info("EVENT_PRIVATE_CONTROLLER: Get events for user with ID = {}.", userId);
        return eventPrivateService.getEventsOfUser(userId, pageable);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEvent(@PathVariable @Positive Long userId, @PathVariable @Positive Long eventId) {
        log.info("EVENT_PRIVATE_CONTROLLER: Get event with ID = {} for user with ID = {}.", eventId, userId);
        return eventPrivateService.getEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable @Positive Long userId,
                                                     @PathVariable @Positive Long eventId,
                                                     @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                                     @RequestParam(name = "size", defaultValue = "10") @Positive int size) {
        Pageable pageable = EwmPageable.of(from, size);
        log.info("EVENT_PRIVATE_CONTROLLER: Get requests for user with ID = {} and event with ID = {}.",
                userId, eventId);
        return eventPrivateService.getRequests(userId, eventId, pageable);
    }

    @PostMapping
    public EventFullDto postEvent(@PathVariable @Positive Long userId, @Valid @RequestBody NewEventDto newEventDto) {
        log.info("EVENT_PRIVATE_CONTROLLER: Post event for user with ID = {}: {}.", userId, newEventDto);
        return eventPrivateService.postEvent(userId, newEventDto);
    }

    @PatchMapping
    public EventFullDto updateEvent(@PathVariable @Positive Long userId,
                                    @Valid @RequestBody UpdateEventDto updateEventDto) {
        log.info("EVENT_PRIVATE_CONTROLLER: Update event for user with ID = {}: {}.", userId, updateEventDto);
        return eventPrivateService.updateEvent(userId, updateEventDto);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEvent(@PathVariable @Positive Long userId, @PathVariable @Positive Long eventId) {
        log.info("EVENT_PRIVATE_CONTROLLER: Cancel event with ID = {} for user with ID = {}.", eventId, userId);
        return eventPrivateService.cancelEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable @Positive Long userId,
                                                  @PathVariable @Positive Long eventId,
                                                  @PathVariable @Positive Long reqId) {
        log.info("EVENT_PRIVATE_CONTROLLER: Confirm request with ID = {} for event with ID = {} and user with ID = {}.",
                reqId, eventId, userId);
        return eventPrivateService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable @Positive Long userId,
                                                 @PathVariable @Positive Long eventId,
                                                 @PathVariable @Positive Long reqId) {
        log.info("EVENT_PRIVATE_CONTROLLER: Reject request with ID = {} for event with ID = {} and user with ID = {}.",
                reqId, eventId, userId);
        return eventPrivateService.rejectRequest(userId, eventId, reqId);
    }
}