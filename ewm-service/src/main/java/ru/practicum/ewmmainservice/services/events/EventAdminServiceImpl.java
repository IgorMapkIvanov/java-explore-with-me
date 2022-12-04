package ru.practicum.ewmmainservice.services.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.dto.events.EventBaseDto;
import ru.practicum.ewmmainservice.dto.events.EventFullDto;
import ru.practicum.ewmmainservice.dto.events.NewEventDto;
import ru.practicum.ewmmainservice.dto.locations.Location;
import ru.practicum.ewmmainservice.enums.State;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.ValidationException;
import ru.practicum.ewmmainservice.mappers.events.EventMapper;
import ru.practicum.ewmmainservice.models.Category;
import ru.practicum.ewmmainservice.models.Event;
import ru.practicum.ewmmainservice.repositories.EventRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventAdminServiceImpl implements EventAdminService {
    private final EventRepository eventRepository;

    /**
     * Admin get events.
     *
     * @param users      {@link List} of {@link Long}
     * @param states     {@link List} of {@link String}
     * @param categories {@link List} of {@link Long}
     * @param rangeStart {@link String}
     * @param rangeEnd   {@link String}
     * @param pageable   {@link Pageable}
     * @return {@link List} of {@link EventFullDto}
     */
    @Override
    public List<EventFullDto> getEvents(List<Long> users, List<String> states, List<Long> categories,
                                        String rangeStart, String rangeEnd, Pageable pageable) {
        log.info("EVENT_ADMIN_SERVICE: Get events for admin with userIds {}, eventStatuses {}, categoryIds {}, rangeStart {}, rangeEnd {}",
                users, states, categories, rangeStart, rangeEnd);

        LocalDateTime currentDate = LocalDateTime.now();
        Page<Event> events = eventRepository.findAll((root, query, criteriaBuilder) ->
                        criteriaBuilder.and(
                                (users != null) ? root.get("initiator").in(users) : root.isNotNull(),
                                (states != null) ? root.get("state").in(states.stream().map(el ->
                                        State.valueOf(el).ordinal()).collect(Collectors.toList())) : root.isNotNull(),
                                (categories != null) ? root.get("categories").in(categories) : root.isNotNull(),
                                (!rangeStart.isEmpty() && !rangeEnd.isEmpty()) ?
                                        criteriaBuilder.and(
                                                criteriaBuilder.greaterThan(root.get("eventDate"),
                                                        LocalDateTime.parse(rangeStart,
                                                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))),
                                                criteriaBuilder.lessThan(root.get("eventDate"),
                                                        LocalDateTime.parse(rangeEnd,
                                                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                        ) : criteriaBuilder.lessThan(root.get("eventDate"), currentDate)
                        ),
                pageable);
        return events.stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    /**
     * Admin update event.
     *
     * @param eventId     {@link Long}
     * @param newEventDto {@link NewEventDto}
     * @return {@link EventFullDto}
     */
    @Override
    @Transactional
    public EventFullDto updateEvent(Long eventId, NewEventDto newEventDto) {
        if (LocalDateTime.now().plusHours(2).isAfter(newEventDto.getEventDate())) {
            String message = "The date of the event must be later than 2 hours from the current";
            String reason = "Incorrect date";
            throw new ValidationException(message, reason);
        }
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    String message = String.format("Event with ID = %s not found", eventId);
                    String reason = "Event not found";
                    throw new NotFoundException(message, reason);
                });
        verifyChange(event, newEventDto);
        if (newEventDto.getLocation() != null) {
            Location location = Location.builder()
                    .lon(newEventDto.getLocation().getLon())
                    .lat(newEventDto.getLocation().getLat())
                    .build();
            event.setLocation(location);
        }
        if (newEventDto.getRequestModeration() != null) {
            event.setRequestModeration(newEventDto.getRequestModeration());
        }
        return EventMapper.toEventFullDto(event);
    }

    /**
     * Admin publish event.
     *
     * @param eventId {@link Long}
     * @return {@link EventFullDto}
     */
    @Override
    @Transactional
    public EventFullDto publishEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    String message = String.format("Event with ID = %s not found", eventId);
                    String reason = "Event not found";
                    throw new NotFoundException(message, reason);
                });
        event.setState(State.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        return EventMapper.toEventFullDto(event);
    }

    /**
     * Admin reject event.
     *
     * @param eventId {@link Long}
     * @return {@link EventFullDto}
     */
    @Override
    @Transactional
    public EventFullDto rejectEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    String message = String.format("Event with ID = %s not found", eventId);
                    String reason = "Event not found";
                    throw new NotFoundException(message, reason);
                });
        event.setState(State.CANCELED);
        return EventMapper.toEventFullDto(event);
    }

    private <T extends EventBaseDto> void verifyChange(Event event, T dto) {
        if (dto.getAnnotation() != null && !dto.getAnnotation().isEmpty()) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            event.setCategories(Category.builder().id(dto.getCategory()).build());
        }
        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            event.setEventDate(dto.getEventDate());
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
            event.setTitle(dto.getTitle());
        }
    }
}