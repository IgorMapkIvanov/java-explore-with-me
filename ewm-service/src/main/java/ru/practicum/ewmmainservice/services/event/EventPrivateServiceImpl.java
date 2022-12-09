package ru.practicum.ewmmainservice.services.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.dto.event.*;
import ru.practicum.ewmmainservice.dto.request.ParticipationRequestDto;
import ru.practicum.ewmmainservice.enums.EventState;
import ru.practicum.ewmmainservice.enums.EventStatus;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.ValidationException;
import ru.practicum.ewmmainservice.mappers.event.EventMapper;
import ru.practicum.ewmmainservice.mappers.request.RequestMapper;
import ru.practicum.ewmmainservice.models.Category;
import ru.practicum.ewmmainservice.models.Event;
import ru.practicum.ewmmainservice.models.Request;
import ru.practicum.ewmmainservice.models.User;
import ru.practicum.ewmmainservice.pageable.EwmPageable;
import ru.practicum.ewmmainservice.repositories.EventRepository;
import ru.practicum.ewmmainservice.repositories.RequestRepository;
import ru.practicum.ewmmainservice.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventPrivateServiceImpl implements EventPrivateService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    /**
     * Get user events.
     *
     * @param userId {@link Long}
     * @return {@link List} of {@link EventShortDto}
     */
    @Override
    public List<EventShortDto> getEventsOfUser(Long userId, Integer from, Integer size) {
        Pageable pageable = EwmPageable.of(from, size, Sort.by(Sort.Direction.ASC, "id"));
        userValidation(userId);
        log.info("EVENT_PRIVATE_SERVICE: Get user with ID = {} events.", userId);
        return eventRepository.findAll(
                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("initiator"),
                                User.builder().id(userId).build()), pageable)
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    /**
     * Update user event.
     *
     * @param userId         {@link Long}
     * @param updateEventDto {@link UpdateEventDto}
     * @return {@link EventFullDto}
     */
    @Override
    @Transactional
    public EventFullDto updateEvent(Long userId, UpdateEventDto updateEventDto) {
        log.info("EVENT_PRIVATE_SERVICE: Update user with ID = {} event with ID = {}.",
                userId, updateEventDto.getEventId());
        userValidation(userId);
        if (LocalDateTime.now().plusHours(2).isAfter(updateEventDto.getEventDate())) {
            String message = "The date of the event must be later than 2 hours from the current";
            String reason = "Incorrect date";
            throw new ValidationException(message, reason);
        }
        Event event = eventRepository.findById(updateEventDto.getEventId())
                .orElseThrow(() -> {
                    String message = String.format("Event with ID = %s not found", updateEventDto.getEventId());
                    String reason = "Event not found";
                    throw new NotFoundException(message, reason);
                });
        if (!event.getInitiator().getId().equals(userId)) {
            String message = String.format("User with ID = %s not initiator of event with ID = %s.", event.getInitiator().getId(), event.getId());
            String reason = "User not initiator of event";
            throw new ValidationException(message, reason);
        }
        if (event.getState().equals(EventState.PUBLISHED)) {
            String message = String.format("Event with ID = %s not PUBLISH.", event.getId());
            String reason = "Event not PUBLISH.";
            throw new ValidationException(message, reason);
        }
        if (event.getState().equals(EventState.CANCELED)) {
            event.setState(EventState.PENDING);
        }
        copyChange(event, updateEventDto);
        log.info("EVENT_PRIVATE_SERVICE: Update event with ID = {} events.",
                event.getId());
        return EventMapper.toEventFullDto(event);
    }

    /**
     * Get user event by id.
     *
     * @param userId  {@link Long}
     * @param eventId {@link Long}
     * @return {@link EventFullDto}
     */
    @Override
    public EventFullDto getEvent(Long userId, Long eventId) {
        userValidation(userId);
        Event event = eventRepository.findByIdAndInitiator(eventId, User.builder().id(userId).build())
                .orElseThrow(() -> {
                    String message = String.format("Event with ID = %s not found", eventId);
                    String reason = "Event not found";
                    throw new NotFoundException(message, reason);
                });
        log.info("EVENT_PRIVATE_SERVICE: Get user with ID = {} event by id = {} events.",
                userId, eventId);
        return EventMapper.toEventFullDto(event);
    }

    /**
     * Post event.
     *
     * @param userId      {@link Long}
     * @param newEventDto {@link NewEventDto}
     * @return {@link EventFullDto}
     */
    @Override
    @Transactional
    public EventFullDto postEvent(Long userId, NewEventDto newEventDto) {
        userValidation(userId);
        if (LocalDateTime.now().plusHours(2).isAfter(newEventDto.getEventDate())) {
            String message = "The date of the event must be later than 2 hours from the current";
            String reason = "Incorrect date";
            throw new ValidationException(message, reason);
        }
        Event event = EventMapper.toEvent(userId, newEventDto);
        log.info("EVENT_PRIVATE_SERVICE: Post user with ID = {} event by id = {} events.",
                userId, event.getId());
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    /**
     * Cancel of event by user
     *
     * @param userId  {@link Long}
     * @param eventId {@link Long}
     * @return {@link EventFullDto}
     */
    @Override
    @Transactional
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        Event event = eventRepository.findByIdAndInitiator(eventId, User.builder().id(userId).build())
                .orElseThrow(() -> {
                    String message = String.format("Event with ID = %s not found", eventId);
                    String reason = "Event not found";
                    throw new NotFoundException(message, reason);
                });
        event.setState(EventState.CANCELED);
        log.info("EVENT_PRIVATE_SERVICE: Cancel of event with ID = {} by user with ID = {} events.",
                eventId, userId);
        return EventMapper.toEventFullDto(event);
    }

    /**
     * Get user event request.
     *
     * @param userId  {@link Long}
     * @param eventId {@link Long}
     * @return {@link List} of {@link ParticipationRequestDto}
     */
    @Override
    public List<ParticipationRequestDto> getRequests(Long userId, Long eventId, Integer from, Integer size) {
        userValidation(userId);
        List<Event> events = eventRepository.findAllByInitiator(User.builder().id(userId).build());
        List<Request> requestList = requestRepository.findAll((root, query, criteriaBuilder) ->
                root.get("event").in(events.stream().map(Event::getId).collect(Collectors.toUnmodifiableList())));
        log.info("EVENT_PRIVATE_SERVICE: Get requests for user with ID = {} and event with ID = {}.",
                userId, eventId);
        return requestList.stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    /**
     * Подтверждение запроса на участие в событии
     *
     * @param userId  Long
     * @param eventId Long
     * @param reqId   Long
     * @return ParticipationRequestDto
     */
    @Override
    @Transactional
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId) {
        userValidation(userId);
        Event event = eventOne(eventId, userId);
        Long requestsCount = requestsCount(event);
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= requestsCount) {
            String message = String.format("The event has reached the limit of requests for participation: %s", event.getParticipantLimit());
            String reason = "Limit of requests for participation";
            throw new ValidationException(message, reason);
        }
        Request request = requestOne(reqId, event);
        if (event.getParticipantLimit() == 0 && !event.getRequestModeration()) {
            return RequestMapper.toParticipationRequestDto(request);
        }
        request.setStatus(EventStatus.CONFIRMED);
        ParticipationRequestDto participationRequestDto = RequestMapper.toParticipationRequestDto(request);
        event.incrementConfirmedRequests();
        eventRepository.save(event);
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == requestsCount + 1) {
            List<Request> requestList = requestRepository.findAll(((root, query, criteriaBuilder) ->
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("event"), event.getId()),
                            criteriaBuilder.equal(root.get("eventStatus"), EventStatus.PENDING.ordinal())
                    )));
            requestList.forEach(el -> el.setStatus(EventStatus.REJECTED));
        }
        log.info("EVENT_PRIVATE_SERVICE: Confirm request with ID = {} of event with ID = {} by user with ID = {} events.",
                reqId, eventId, userId);
        return participationRequestDto;
    }

    /**
     * Отклонение заявки на участие в событии
     *
     * @param userId  Long
     * @param eventId Long
     * @param reqId   Long
     * @return ParticipationRequestDto
     */
    @Override
    @Transactional
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId) {
        userValidation(userId);
        Event event = eventOne(eventId, userId);
        Request request = requestOne(reqId, event);
        if (request.getStatus().equals(EventStatus.CONFIRMED)) {
            event.decrementConfirmedRequests();
            eventRepository.save(event);
        }
        request.setStatus(EventStatus.REJECTED);
        log.info("EVENT_PRIVATE_SERVICE: Reject request with ID = {} of event with ID = {} by user with ID = {} events.",
                reqId, eventId, userId);
        return RequestMapper.toParticipationRequestDto(request);
    }

    private void userValidation(Long id) {
        if (!userRepository.existsById(id)) {
            String message = String.format("User with ID = %s not found.", id);
            String reason = "User not found.";
            throw new NotFoundException(message, reason);
        }
    }

    private Event eventOne(Long eventId, Long userId) {
        return eventRepository.findOne(((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("id"), eventId),
                        criteriaBuilder.equal(root.get("initiator"),
                                userId),
                        criteriaBuilder.equal(root.get("state"), EventState.PUBLISHED.ordinal())
                ))).orElseThrow(() -> {
            String message = String.format("Event with ID = %s not found.", eventId);
            String reason = "Event not found";
            throw new NotFoundException(message, reason);
        });
    }

    private Request requestOne(Long reqId, Event event) {
        return requestRepository.findOne((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("id"), reqId),
                        criteriaBuilder.equal(root.get("event"), event.getId())
                )).orElseThrow(() -> {
            String message = String.format("Request with ID = %s not found.", reqId);
            String reason = "Request not found";
            throw new NotFoundException(message, reason);
        });
    }

    private Long requestsCount(Event event) {
        return requestRepository.count((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("event"), event.getId()),
                        criteriaBuilder.equal(root.get("status"), EventStatus.CONFIRMED.ordinal())
                ));
    }

    private <T extends EventBaseDto> void copyChange(Event event, T dto) {
        if (dto.getAnnotation() != null && (!dto.getAnnotation().isEmpty())) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            event.setCategories(Category.builder().id(dto.getCategory()).build());
        }
        if (dto.getDescription() != null && (!dto.getDescription().isEmpty())) {
            event.setDescription(dto.getDescription());
        }
        if ((dto.getEventDate()) != null) {
            event.setEventDate(dto.getEventDate());
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getTitle() != null && (!dto.getTitle().isEmpty())) {
            event.setTitle(dto.getTitle());
        }
    }
}