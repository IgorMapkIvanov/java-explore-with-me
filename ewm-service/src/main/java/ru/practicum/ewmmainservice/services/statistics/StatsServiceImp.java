package ru.practicum.ewmmainservice.services.statistics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.dto.events.EventFullDto;
import ru.practicum.ewmmainservice.dto.events.EventShortDto;
import ru.practicum.ewmmainservice.dto.statistics.EndpointHitDto;
import ru.practicum.ewmmainservice.dto.statistics.ViewStatsDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class StatsServiceImp implements StatsService {
    @Value("${stats-server.url}")
    private String serverUrl;
    private final ObjectMapper objectMapper;

    public StatsServiceImp(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void statsHit(HttpServletRequest request) throws URISyntaxException, JsonProcessingException {
        EndpointHitDto endpointHitDto = EndpointHitDto.builder()
                .app("ewm-main-service")
                .uri(request.getRequestURL().toString())
                .timestamp(LocalDateTime.now())
                .ip(request.getRemoteAddr())
                .build();
        sendStats(endpointHitDto);
    }

    @Override
    public List<EventShortDto> getViewStats(HttpServletRequest request, List<EventShortDto> eventShortDtoList)
            throws URISyntaxException, IOException, InterruptedException {
        List<String> uris = eventShortDtoList.stream()
                .map(el -> "/events/" + el.getId().toString())
                .collect(Collectors.toList());
        List<ViewStatsDto> statsViewDtoList = getStats(uris);
        Map<String, ViewStatsDto> statsViewDtoMap = statsViewDtoList.stream()
                .collect(Collectors.toMap(ViewStatsDto::getUri, v -> v));
        eventShortDtoList.forEach(el -> {
            String key = "/events/" + el.getId().toString();
            ViewStatsDto stats = new ViewStatsDto();
            Long hits = statsViewDtoMap.getOrDefault(key, stats).getHits();
            el.setViews(hits);
        });
        statsHit(request, uris);
        return eventShortDtoList;
    }

    @Override
    public EventFullDto getViewStats(HttpServletRequest request, EventFullDto eventFullDto)
            throws URISyntaxException, IOException, InterruptedException {
        List<String> uris = List.of("/events/" + eventFullDto.getId().toString());
        List<ViewStatsDto> statsViewDto = getStats(uris);
        Long view = (statsViewDto.size() != 0) ? statsViewDto.get(0).getHits() : 0L;
        eventFullDto.setViews(view);
        statsHit(request, uris);
        return eventFullDto;
    }

    private void statsHit(HttpServletRequest request, List<String> uris)
            throws JsonProcessingException, URISyntaxException {
        List<EndpointHitDto> endpointHitDtos = uris.stream()
                .map(uri -> EndpointHitDto.builder()
                        .app("ewm-main-service")
                        .uri(uri)
                        .timestamp(LocalDateTime.now())
                        .ip(request.getRemoteAddr())
                        .build())
                .collect(Collectors.toList());
        for (EndpointHitDto statsHitDto : endpointHitDtos) {
            sendStats(statsHitDto);
        }
    }

    private void sendStats(EndpointHitDto endpointHitDto) throws JsonProcessingException, URISyntaxException {
        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(endpointHitDto);

        HttpRequest statRequest = HttpRequest.newBuilder(new URI(serverUrl + "/hit"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient.newHttpClient()
                .sendAsync(statRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode);
    }

    private List<ViewStatsDto> getStats(List<String> uris)
            throws URISyntaxException, IOException, InterruptedException {
        URI uri = new URIBuilder(serverUrl + "/stats")
                .addParameter("start",
                        LocalDateTime.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addParameter("end",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addParameter("uris", listToString(uris))
                .build();

        HttpRequest statRequest = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(statRequest, HttpResponse.BodyHandlers.ofString());
        log.warn("response.body() - {}", response.body());
        return objectMapper.readValue(response.body(), new TypeReference<>() {
        });
    }

    private String listToString(List<String> uris) {
        StringBuilder sb = new StringBuilder();
        var endSize = uris.size() - 1;
        for (int i = 0; i < uris.size(); i++) {
            sb.append(uris.get(i));
            if (i != endSize) sb.append(",");
        }
        return sb.toString();
    }
}