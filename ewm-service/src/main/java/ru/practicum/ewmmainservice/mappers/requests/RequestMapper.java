package ru.practicum.ewmmainservice.mappers.requests;

import ru.practicum.ewmmainservice.dto.requests.ParticipationRequestDto;
import ru.practicum.ewmmainservice.models.Request;

public class RequestMapper {

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return new ParticipationRequestDto(request.getId(),
                request.getEvent().getId(),
                request.getRequester().getId(),
                request.getCreated().toString(),
                request.getStatus().toString());
    }
}