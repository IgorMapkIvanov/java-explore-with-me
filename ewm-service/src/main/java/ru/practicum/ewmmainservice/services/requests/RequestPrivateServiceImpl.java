package ru.practicum.ewmmainservice.services.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.dto.requests.ParticipationRequestDto;
import ru.practicum.ewmmainservice.enums.State;
import ru.practicum.ewmmainservice.enums.Status;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.ValidationException;
import ru.practicum.ewmmainservice.mappers.requests.RequestMapper;
import ru.practicum.ewmmainservice.models.Event;
import ru.practicum.ewmmainservice.models.Request;
import ru.practicum.ewmmainservice.models.User;
import ru.practicum.ewmmainservice.pageable.EwmPageable;
import ru.practicum.ewmmainservice.repositories.EventRepository;
import ru.practicum.ewmmainservice.repositories.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestPrivateServiceImpl implements RequestPrivateService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId, Integer from, Integer size) {
        Pageable pageable = EwmPageable.of(from, size, Sort.by(Sort.Direction.ASC, "id"));
        List<Request> requestList = requestRepository.findAllByRequester(User.builder().id(userId).build(), pageable);
        log.info("REQUEST_PRIVATE_SERVICE: Get request for user with ID = {}.", userId);
        return requestList.stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        log.info("REQUEST_PRIVATE_SERVICE: Add request from user with ID = {} for event with ID = {}.",
                userId, eventId);
        Request request = Request.builder()
                .created(LocalDateTime.now())
                .event(Event.builder().id(eventId).build())
                .requester(User.builder().id(userId).build())
                .status(Status.PENDING)
                .build();
        Event event = eventOne(eventId, userId);
        Long requestsCount = requestsCount(event);
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= requestsCount) {
            String message = String.format("Event participant limit is full: %s", event.getParticipantLimit());
            String reason = "Event participant limit is full";
            throw new ValidationException(message, reason);
        }
        requestRepository.findByRequesterAndEvent(
                        User.builder().id(userId).build(),
                        Event.builder().id(eventId).build())
                .ifPresent(rq -> {
                    String message = "Can't add a repeat request";
                    String reason = "Repeat request";
                    throw new ValidationException(message, reason);
                });
        if (!event.getRequestModeration()) {
            request.setStatus(Status.CONFIRMED);
            event.incrementConfirmedRequests();
            eventRepository.save(event);
        }
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        log.info("REQUEST_PRIVATE_SERVICE: Cancel request with ID = {} from user with ID = {}.",
                requestId, userId);
        Request request = requestRepository.findByIdAndRequester(requestId, User.builder().id(userId).build())
                .orElseThrow(() -> {
                    String message = String.format("Request with ID = %s not found", requestId);
                    String reason = "Request not found";
                    throw new NotFoundException(message, reason);
                });
        Event event = eventRepository.findOne(((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("id"), request.getEvent().getId()),
                        criteriaBuilder.equal(root.get("state"), State.PUBLISHED.ordinal())
                ))).orElseThrow(() -> {
            String message = "Event not found";
            String reason = "Event not found";
            throw new NotFoundException(message, reason);
        });
        if (request.getStatus().equals(Status.CONFIRMED)) {
            event.decrementConfirmedRequests();
            eventRepository.save(event);
        }
        request.setStatus(Status.CANCELED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    private Event eventOne(Long eventId, Long userId) {
        return eventRepository.findOne(((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("id"), eventId),
                        criteriaBuilder.notEqual(root.get("initiator"), userId),
                        criteriaBuilder.equal(root.get("state"), State.PUBLISHED.ordinal())
                ))).orElseThrow(() -> {
            String message = String.format("Event with ID = %s not found", eventId);
            String reason = "Event not found";
            throw new NotFoundException(message, reason);
        });
    }

    private Long requestsCount(Event event) {
        return requestRepository.count((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("event"), event.getId()),
                        criteriaBuilder.equal(root.get("status"), Status.CONFIRMED.ordinal())
                ));
    }
}