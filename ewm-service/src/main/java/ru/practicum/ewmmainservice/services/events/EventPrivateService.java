package ru.practicum.ewmmainservice.services.events;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.dto.events.EventFullDto;
import ru.practicum.ewmmainservice.dto.events.EventShortDto;
import ru.practicum.ewmmainservice.dto.events.NewEventDto;
import ru.practicum.ewmmainservice.dto.events.UpdateEventDto;
import ru.practicum.ewmmainservice.dto.requests.ParticipationRequestDto;

import java.util.List;

public interface EventPrivateService {
    EventFullDto getEvent(Long userId, Long eventId);

    List<EventShortDto> getEventsOfUser(Long userId, Pageable pageable);

    List<ParticipationRequestDto> getRequests(Long userId, Long eventId, Pageable pageable);

    EventFullDto postEvent(Long userId, NewEventDto dto);

    EventFullDto cancelEvent(Long userId, Long eventId);

    EventFullDto updateEvent(Long userId, UpdateEventDto updateEventDto);

    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId);
}