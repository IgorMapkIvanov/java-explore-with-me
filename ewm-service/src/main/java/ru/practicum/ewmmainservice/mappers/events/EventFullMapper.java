package ru.practicum.ewmmainservice.mappers.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.dto.events.EventFullDto;
import ru.practicum.ewmmainservice.dto.locations.Location;
import ru.practicum.ewmmainservice.enums.State;
import ru.practicum.ewmmainservice.enums.Status;
import ru.practicum.ewmmainservice.mappers.categories.CategoryMapper;
import ru.practicum.ewmmainservice.mappers.users.UserShortDtoMapper;
import ru.practicum.ewmmainservice.models.Event;
import ru.practicum.ewmmainservice.repositories.RequestRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Lazy
@Component
@RequiredArgsConstructor
public class EventFullMapper {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final RequestRepository requestRepository;
    @Lazy
    private final UserShortDtoMapper userShortDtoMapper;

    public EventFullDto toEventFullDto(Event event) {
        Long eventConfirmedRequest = 0L;
        if (requestRepository.countByEventIdAndStatus(event.getId(), Status.CONFIRMED) != null) {
            eventConfirmedRequest = requestRepository.countByEventIdAndStatus(event.getId(), Status.CONFIRMED);
        }
        return new EventFullDto(event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                event.getDescription(),
                event.getEventDate().format(formatter),
                new Location(event.getLat(), event.getLon()),
                CategoryMapper.toCategoryDto(event.getCategory()),
                event.getPaid(),
                event.getParticipantLimit(),
                userShortDtoMapper.toUserShortDto(event.getInitiator()),
                event.getCreatedOn().format(formatter),
                event.getPublishedOn() != null ? event.getPublishedOn().format(formatter) : null,
                event.getRequestModeration(),
                event.getState().toString(),
                eventConfirmedRequest,
                0L);
    }

    public Event toEvent(EventFullDto dto) {
        Converter<String, State> converter = source -> {
            try {
                return source.isEmpty() ? null : State.valueOf(source.trim().toUpperCase(Locale.ROOT));
            } catch (Exception e) {
                return State.UNSUPPORTED_STATE;
            }
        };
        return new Event(dto.getId(),
                dto.getTitle(),
                dto.getAnnotation(),
                dto.getDescription(),
                LocalDateTime.parse(dto.getEventDate(), formatter),
                dto.getLocation().getLat(),
                dto.getLocation().getLon(),
                CategoryMapper.toCategory(dto.getCategory()),
                dto.getPaid(),
                dto.getParticipantLimit(),
                userShortDtoMapper.toUser(dto.getInitiator()),
                LocalDateTime.parse(dto.getCreatedOn(), formatter),
                dto.getPublishedOn() != null ? LocalDateTime.parse(dto.getPublishedOn(), formatter) : null,
                dto.getRequestModeration(),
                converter.convert(dto.getState())
        );
    }
}