package ru.practicum.ewmmainservice.mappers.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.dto.events.EventShortDto;
import ru.practicum.ewmmainservice.dto.locations.Location;
import ru.practicum.ewmmainservice.enums.Status;
import ru.practicum.ewmmainservice.mappers.categories.CategoryMapper;
import ru.practicum.ewmmainservice.mappers.users.UserShortDtoMapper;
import ru.practicum.ewmmainservice.models.Event;
import ru.practicum.ewmmainservice.repositories.RequestRepository;

import java.time.format.DateTimeFormatter;

@Lazy
@Component
@RequiredArgsConstructor
public class EventShortMapper {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final RequestRepository requestRepository;
    @Lazy
    private final UserShortDtoMapper userShortDtoMapper;

    public EventShortDto toEventShortDto(Event event) {
        Long eventConfirmedRequest = 0L;
        if (requestRepository.countByEventIdAndStatus(event.getId(), Status.CONFIRMED) != null) {
            eventConfirmedRequest = requestRepository.countByEventIdAndStatus(event.getId(), Status.CONFIRMED);
        }
        return new EventShortDto(event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                event.getDescription(),
                event.getEventDate().format(formatter),
                new Location(event.getLat(), event.getLon()),
                CategoryMapper.toCategoryDto(event.getCategory()),
                event.getPaid(),
                userShortDtoMapper.toUserShortDto(event.getInitiator()),
                eventConfirmedRequest,
                0L);
    }
}