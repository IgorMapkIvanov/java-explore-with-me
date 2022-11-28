package ru.practicum.ewmmainservice.services.events;

import ru.practicum.ewmmainservice.dto.events.EventFullDto;
import ru.practicum.ewmmainservice.dto.events.EventShortDto;
import ru.practicum.ewmmainservice.enums.Sort;
import ru.practicum.ewmmainservice.models.Event;

import java.util.List;

public interface EventPublicService {
    Event getById(Long id);

    List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd, boolean isAvailable, Sort sort, int from, int size);

    EventFullDto getEventById(Long id);
}