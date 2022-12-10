package ru.practicum.ewmmainservice.services.event;

import ru.practicum.ewmmainservice.dto.event.EventFullDto;
import ru.practicum.ewmmainservice.dto.event.NewEventDto;

import java.util.List;


public interface EventAdminService {
    List<EventFullDto> getEvents(List<Long> users, List<String> states, List<Long> categories,
                                 String rangeStart, String rangeEnd, Integer from, Integer size);

    EventFullDto updateEvent(Long eventId, NewEventDto newEventDto);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);
}