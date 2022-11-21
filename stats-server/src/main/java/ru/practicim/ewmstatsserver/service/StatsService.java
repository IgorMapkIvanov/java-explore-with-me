package ru.practicim.ewmstatsserver.service;

import reactor.core.publisher.Mono;
import ru.practicim.ewmstatsserver.dto.EndpointHitDto;
import ru.practicim.ewmstatsserver.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    Mono<List<ViewStats>> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

    Mono<EndpointHitDto> addEndpoint(EndpointHitDto endpointHitDto);
}
