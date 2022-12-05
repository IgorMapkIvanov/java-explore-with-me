package ru.practicim.ewmstatsserver.service;

import ru.practicim.ewmstatsserver.dto.EndpointHitDto;
import ru.practicim.ewmstatsserver.dto.ViewStatsDto;

import java.util.List;

public interface StatsService {
    void addHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getViewStats(String start, String end, List<String> uris, Boolean unique);
}