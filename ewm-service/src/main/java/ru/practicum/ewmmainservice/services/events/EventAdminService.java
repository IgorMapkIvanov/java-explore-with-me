package ru.practicum.ewmmainservice.services.events;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.dto.events.EventFullDto;
import ru.practicum.ewmmainservice.dto.events.NewEventDto;

import java.util.List;


public interface EventAdminService {
    List<EventFullDto> getEvents(List<Long> users, List<String> states, List<Long> categories,
                                 String rangeStart, String rangeEnd, Pageable pageable);

    EventFullDto updateEvent(Long eventId, NewEventDto newEventDto);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);
}