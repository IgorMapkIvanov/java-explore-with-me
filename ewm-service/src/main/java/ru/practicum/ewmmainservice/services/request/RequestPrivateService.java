package ru.practicum.ewmmainservice.services.request;

import ru.practicum.ewmmainservice.dto.request.ParticipationRequestDto;

import java.util.List;

public interface RequestPrivateService {
    List<ParticipationRequestDto> getUserRequests(Long userId, Integer from, Integer size);

    ParticipationRequestDto addRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}