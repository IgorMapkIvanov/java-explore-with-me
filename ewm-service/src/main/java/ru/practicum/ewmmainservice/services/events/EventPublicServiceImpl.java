package ru.practicum.ewmmainservice.services.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.client.EventsClient;
import ru.practicum.ewmmainservice.client.statistic.EndpointHit;
import ru.practicum.ewmmainservice.client.statistic.ViewHits;
import ru.practicum.ewmmainservice.dto.events.EventFullDto;
import ru.practicum.ewmmainservice.dto.events.EventShortDto;
import ru.practicum.ewmmainservice.enums.State;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.mappers.events.EventMapper;
import ru.practicum.ewmmainservice.models.Event;
import ru.practicum.ewmmainservice.repositories.EventRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventPublicServiceImpl implements EventPublicService {
    private final EventRepository eventRepository;
    private final EventsClient eventsClient;


    /**
     * Get events with params
     *
     * @param text        {@link String}
     * @param categories  {@link List} of {@link Long}
     * @param paid        {@link Boolean}
     * @param rangeStart  {@link String}
     * @param rangeEnd    {@link String}
     * @param isAvailable {@link Boolean}
     * @param pageable    {@link Pageable}
     * @return {@link List} of {@link EventShortDto}
     */
    @Override
    public List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid, String rangeStart,
                                         String rangeEnd, Boolean isAvailable, HttpServletRequest request, Pageable pageable) {
        log.info("EVENT_PUBLIC_SERVICE: Get events with params: text {}, categories {}, isPaid {}, rangeStart {}," +
                        " rangeEnd {}, isOnlyAvailable {}",
                text, categories, paid, rangeStart, rangeEnd, isAvailable);
        log.info("EVENT_PUBLIC_SERVICE: Post request to stats server");
        addStat(request);
        LocalDateTime currentDate = LocalDateTime.now();
        Page<Event> events = eventRepository.findAll((root, query, criteriaBuilder) ->
                        criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("state"), State.PUBLISHED.ordinal()),
                                root.get("categories").in(categories),
                                criteriaBuilder.equal(root.get("paid"), paid),
                                (!rangeStart.isEmpty() && !rangeEnd.isEmpty()) ?
                                        criteriaBuilder.and(
                                                criteriaBuilder.greaterThan(root.get("eventDate"),
                                                        LocalDateTime.parse(rangeStart,
                                                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))),
                                                criteriaBuilder.lessThan(root.get("eventDate"),
                                                        LocalDateTime.parse(rangeEnd,
                                                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                        ) : criteriaBuilder.lessThan(root.get("eventDate"), currentDate),
                                (isAvailable) ? criteriaBuilder.or(
                                        criteriaBuilder.equal(root.get("participantLimit"), 0),
                                        criteriaBuilder.and(
                                                criteriaBuilder.notEqual(root.get("participantLimit"), 0),
                                                criteriaBuilder.greaterThan(root.get("participantLimit"),
                                                        root.get("confirmedRequests"))
                                        )) : root.isNotNull(),
                                criteriaBuilder.or(
                                        criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")),
                                                "%" + text.toLowerCase() + "%"),
                                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                                                "%" + text.toLowerCase() + "%")
                                )),
                pageable);
        addViews(events);
        return events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());

    }

    /**
     * Get event by id
     *
     * @param id {@link Long}
     * @return {@link  EventFullDto}
     */
    @Override
    public EventFullDto getEventById(Long id, HttpServletRequest request) {
        log.info("EVENT_PUBLIC_SERVICE: Get event with ID = {}.", id);
        log.info("EVENT_PUBLIC_SERVICE: Post request to stats server");
        addStat(request);
        Event event = eventRepository.findByIdAndState(id, State.PUBLISHED)
                .orElseThrow(() -> {
                    String message = String.format("Event with ID = %s not found.", id);
                    String reason = "Event not found";
                    throw new NotFoundException(message, reason);
                });
        addViews(event, List.of(request.getRequestURI()));
        return EventMapper.toEventFullDto(event);
    }

    private void addStat(HttpServletRequest request) {
        EndpointHit endpointHit = new EndpointHit(
                "ewm-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        eventsClient.saveStat(endpointHit);
    }

    private void addViews(Event event, List<String> uris) {
        ResponseEntity<ViewHits[]> responseEntity = eventsClient.getStat(uris);
        ViewHits[] stat = responseEntity.getBody();
        assert stat != null;
        if (stat.length < 1) {
            event.setViews(0);
        }
        event.setViews(stat[0].getHits());
    }

    private void addViews(Page<Event> events) {
        List<String> uris = new ArrayList<>();
        for (Event event : events) {
            uris.add("/events/" + event.getId());
        }
        ResponseEntity<ViewHits[]> responseEntity = eventsClient.getStat(uris);
        ViewHits[] stat = responseEntity.getBody();
        assert stat != null;
        Map<Long, Integer> views = new HashMap<>();
        for (ViewHits viewHits : stat) {
            String[] array = viewHits.getUri().split("/");
            views.put(Long.valueOf(array[array.length - 1]), viewHits.getHits());
        }
        for (Event event : events) {
            if (views.get(event.getId()) == null) {
                event.setViews(0);
            } else {
                event.setViews(views.get(event.getId()));
            }
        }
    }
}