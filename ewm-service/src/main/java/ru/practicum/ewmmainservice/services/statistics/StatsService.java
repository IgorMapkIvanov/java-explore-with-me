package ru.practicum.ewmmainservice.services.statistics;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.practicum.ewmmainservice.dto.events.EventFullDto;
import ru.practicum.ewmmainservice.dto.events.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface StatsService {
    void statsHit(HttpServletRequest request) throws URISyntaxException, JsonProcessingException;

    List<EventShortDto> getViewStats(HttpServletRequest request, List<EventShortDto> eventShortDtoList)
            throws URISyntaxException, IOException, InterruptedException;

    EventFullDto getViewStats(HttpServletRequest request, EventFullDto eventFullDto)
            throws URISyntaxException, IOException, InterruptedException;
}