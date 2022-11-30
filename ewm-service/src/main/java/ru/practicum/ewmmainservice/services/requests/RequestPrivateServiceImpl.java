package ru.practicum.ewmmainservice.services.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
import ru.practicum.ewmmainservice.repositories.EventRepository;
import ru.practicum.ewmmainservice.repositories.RequestRepository;
import ru.practicum.ewmmainservice.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestPrivateServiceImpl implements RequestPrivateService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;


    /**
     * Get user requests.
     *
     * @param userId {@link Long}
     * @return {@link  List} of {@link ParticipationRequestDto}
     */
    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId, Pageable pageable) {
        User user = checkUserInDbAndReturn(userId);
        log.info("REQUEST_PRIVATE_SERVICE: Get requests for user with ID = {}.", userId);
        return requestRepository.findByRequesterId(user.getId(), pageable).stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    /**
     * Add request.
     *
     * @param userId  {@link Long}
     * @param eventId {@link Long}
     * @return {@link ParticipationRequestDto}
     */
    @Override
    @Transactional
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        User user = checkUserInDbAndReturn(userId);
        Event event = checkEventInDbAndReturn(eventId);
        Long eventConfirmedRequest = getEventConfirmedRequest(eventId);

        requestValidation(user, event, eventConfirmedRequest);

        Request request = new Request(0L,
                LocalDateTime.now(),
                event,
                user,
                event.getRequestModeration() ? Status.PENDING : Status.CONFIRMED);
        ParticipationRequestDto participationRequestDto = RequestMapper.toParticipationRequestDto(requestRepository.save(request));
        log.info("REQUEST_PRIVATE_SERVICE: Add request: ID = {}.", participationRequestDto.getId());
        return participationRequestDto;
    }

    /**
     * Cancel request in event.
     *
     * @param userId    {@link Long}
     * @param requestId {@link Long}
     * @return {@link ParticipationRequestDto}
     */
    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        User user = checkUserInDbAndReturn(userId);
        Request request = checkRequestInDbAndReturn(requestId);

        if (!Objects.equals(userId, request.getRequester().getId())) {
            String message = String.format("User with ID = %s did not create request with ID = %s", user.getId(), requestId);
            String reason = "User did not create request";
            throw new ValidationException(message, reason);
        }
        request.setStatus(Status.CANCELED);
        log.info("REQUEST_PRIVATE_SERVICE: User with ID = {} cancel request with ID = {}.", user.getId(), request.getId());
        return RequestMapper.toParticipationRequestDto(request);
    }

    private Long getEventConfirmedRequest(Long eventId) {
        if (requestRepository.countByEventIdAndStatus(eventId, Status.CONFIRMED) != null) {
            return requestRepository.countByEventIdAndStatus(eventId, Status.CONFIRMED);
        } else {
            return 0L;
        }
    }

    private void requestValidation(User user, Event event, Long eventConfirmedRequest) {
        String message;
        String reason;
        if (requestRepository.findByEventIdAndRequesterId(event.getId(), user.getId()) != null) {
            message = "Request for participation in event cannot be added again";
            reason = "Re-request";
            throw new ValidationException(message, reason);
        }
        if (Objects.equals(user.getId(), event.getInitiator().getId())) {
            message = "Event initiator cannot send request";
            reason = "Initiator request";
            throw new ValidationException(message, reason);
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            message = "Event isn't published";
            reason = "Not published";
            throw new ValidationException(message, reason);
        }
        if (event.getParticipantLimit() != 0 && Objects.equals(event.getParticipantLimit(), eventConfirmedRequest)) {
            message = String.format("Limit of requests for event with ID = %s has been exhausted", event.getId());
            reason = "Limit exhausted";
            throw new ValidationException(message, reason);
        }
    }

    private User checkUserInDbAndReturn(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            String message = String.format("User with ID = '%s', not found", id);
            String reason = "User not found";
            throw new NotFoundException(message, reason);
        });
    }

    private Event checkEventInDbAndReturn(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Event with ID = '%s', not found", id);
            String reason = "Event not found";
            throw new NotFoundException(message, reason);
        });
    }

    private Request checkRequestInDbAndReturn(Long id) {
        return requestRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Request with ID = '%s', not found", id);
            String reason = "Request not found";
            throw new NotFoundException(message, reason);
        });
    }
}