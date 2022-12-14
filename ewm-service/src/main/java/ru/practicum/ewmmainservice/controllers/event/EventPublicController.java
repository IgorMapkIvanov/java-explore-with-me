package ru.practicum.ewmmainservice.controllers.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.event.EventFullDto;
import ru.practicum.ewmmainservice.dto.event.EventShortDto;
import ru.practicum.ewmmainservice.enums.EventSort;
import ru.practicum.ewmmainservice.exceptions.ValidationException;
import ru.practicum.ewmmainservice.pageable.EwmPageable;
import ru.practicum.ewmmainservice.services.event.EventPublicService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/events")
public class EventPublicController {
    private final EventPublicService eventPublicService;

    @GetMapping(value = "/{id}")
    public EventFullDto getEventById(@PathVariable @Positive Long id, HttpServletRequest request) {
        log.info("EVENT_PUBLIC_CONTROLLER: Get event by ID = {}.", id);
        return eventPublicService.getEventById(id, request);
    }

    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(name = "text", required = false) String text,
                                         @RequestParam(name = "categories", required = false) List<Long> categories,
                                         @RequestParam(name = "paid", required = false) Boolean paid,
                                         @RequestParam(name = "rangeStart", required = false,
                                                 defaultValue = "") String rangeStart,
                                         @RequestParam(name = "rangeEnd", required = false,
                                                 defaultValue = "") String rangeEnd,
                                         @RequestParam(name = "onlyAvailable", required = false,
                                                 defaultValue = "false") Boolean onlyAvailable,
                                         @RequestParam(name = "sort", required = false,
                                                 defaultValue = "ID") String sort,
                                         @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                         @RequestParam(name = "size", defaultValue = "10") @Positive int size,
                                         HttpServletRequest request) {
        log.info("EVENT_PUBLIC_CONTROLLER: Get events with params: text {}, categories {}, isPaid {}, rangeStart {}," +
                        " rangeEnd {}, isOnlyAvailable {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable);
        EventSort eventSort;
        try {
            eventSort = EventSort.valueOf(sort);
        } catch (IllegalArgumentException e) {
            String message = String.format("Sort type incorrect : %s", sort);
            String reason = "Sort type incorrect";
            throw new ValidationException(message, reason);
        }
        Pageable pageable;
        switch (eventSort) {
            case EVENT_DATE:
                pageable = EwmPageable.of(from, size, Sort.by(Sort.Direction.ASC, "eventDate"));
                break;
            case VIEWS:
                pageable = EwmPageable.of(from, size, Sort.by(Sort.Direction.ASC, "views"));
                break;
            default:
                pageable = EwmPageable.of(from, size, Sort.by(Sort.Direction.ASC, "id"));
        }
        List<EventShortDto> eventShortDtoList = eventPublicService
                .getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, request, pageable);
        log.info("EVENT_PUBLIC_CONTROLLER: Post request to stats server");
        return eventShortDtoList;
    }
}