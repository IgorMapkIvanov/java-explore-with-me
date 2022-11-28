package ru.practicum.ewmmainservice.services.requests;

import ru.practicum.ewmmainservice.dto.requests.ParticipationRequestDto;

import java.util.List;

public interface RequestPrivateService {
    List<ParticipationRequestDto> getUserRequests(Long userId);

    ParticipationRequestDto addRequest(Long userId, Long eventId);


    ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}