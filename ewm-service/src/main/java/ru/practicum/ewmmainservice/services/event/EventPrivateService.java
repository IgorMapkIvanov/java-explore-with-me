package ru.practicum.ewmmainservice.services.event;

import ru.practicum.ewmmainservice.dto.event.EventFullDto;
import ru.practicum.ewmmainservice.dto.event.EventShortDto;
import ru.practicum.ewmmainservice.dto.event.NewEventDto;
import ru.practicum.ewmmainservice.dto.event.UpdateEventDto;
import ru.practicum.ewmmainservice.dto.request.ParticipationRequestDto;

import java.util.List;

public interface EventPrivateService {
    EventFullDto getEvent(Long userId, Long eventId);

    List<EventShortDto> getEventsOfUser(Long userId, Integer from, Integer size);

    List<ParticipationRequestDto> getRequests(Long userId, Long eventId, Integer from, Integer size);

    EventFullDto postEvent(Long userId, NewEventDto dto);

    EventFullDto cancelEvent(Long userId, Long eventId);

    EventFullDto updateEvent(Long userId, UpdateEventDto updateEventDto);

    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId);
}