package ru.practicum.ewmmainservice.services.event;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.dto.event.EventFullDto;
import ru.practicum.ewmmainservice.dto.event.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventPublicService {
    List<EventShortDto> getEvents(String text,
                                  List<Long> categories,
                                  Boolean paid,
                                  String rangeStart,
                                  String rangeEnd,
                                  Boolean isAvailable,
                                  HttpServletRequest request,
                                  Pageable pageable);

    EventFullDto getEventById(Long id, HttpServletRequest request);
}