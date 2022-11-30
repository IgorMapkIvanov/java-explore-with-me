package ru.practicum.ewmmainservice.services.requests;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.dto.requests.ParticipationRequestDto;

import java.util.List;

public interface RequestPrivateService {
    List<ParticipationRequestDto> getUserRequests(Long userId, Pageable pageable);

    ParticipationRequestDto addRequest(Long userId, Long eventId);


    ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}