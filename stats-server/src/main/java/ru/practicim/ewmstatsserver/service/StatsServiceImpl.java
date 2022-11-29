package ru.practicim.ewmstatsserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import ru.practicim.ewmstatsserver.EndpointHitMapper;
import ru.practicim.ewmstatsserver.dto.EndpointHitDto;
import ru.practicim.ewmstatsserver.model.EndpointHit;
import ru.practicim.ewmstatsserver.model.ViewStats;
import ru.practicim.ewmstatsserver.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    public Mono<EndpointHitDto> addEndpoint(EndpointHitDto endpointHitDto) {
        String[] split = endpointHitDto.getUri().split("/");
        endpointHitDto.setUri("/" + split[1]);
        endpointHitDto.setUriId(split[2]);
        return statsRepository
                .save(EndpointHitMapper.toEndpointHit(endpointHitDto))
                .map(EndpointHitMapper::toEndpointHitDto)
                //Logging
                .log("Service -> addEndPoint",
                        Level.INFO,
                        SignalType.ON_SUBSCRIBE, SignalType.ON_NEXT, SignalType.ON_COMPLETE);
    }

    @Override
    public Mono<List<ViewStats>> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (uris == null || uris.isEmpty()) {
            log.info("Service -> getStats return empty collection,because uris is empty.");
            return Mono.just(Collections.emptyList());
        } else {
            if (unique) {
                return statsRepository.findAllByTimestampBetweenAndUriIn(start, end, uris)
                        .buffer()
                        .next()
                        .map(endpointHits -> getViewStatsUnique(uris, endpointHits))
                        //Logging
                        .log("Service -> getStats",
                                Level.INFO,
                                SignalType.ON_SUBSCRIBE, SignalType.ON_COMPLETE)
                        .log("uris = " + uris.size() + ", unique = true",
                                Level.INFO,
                                SignalType.ON_SUBSCRIBE, SignalType.ON_COMPLETE)
                        .log(start.toString(),
                                Level.INFO,
                                SignalType.ON_SUBSCRIBE, SignalType.ON_COMPLETE)
                        .log(end.toString(),
                                Level.INFO,
                                SignalType.ON_SUBSCRIBE, SignalType.ON_COMPLETE)
                        .log("Service -> getStats return",
                                Level.INFO,
                                SignalType.ON_NEXT);
            } else {
                return statsRepository.findAllByTimestampBetweenAndUriIn(start, end, uris)
                        .buffer()
                        .next()
                        .map(endpointHits -> getViewStats(uris, endpointHits))
                        //Logging
                        .log("Service -> getStats",
                                Level.INFO,
                                SignalType.ON_SUBSCRIBE, SignalType.ON_COMPLETE)
                        .log("uris = " + uris.size() + ", unique = false",
                                Level.INFO,
                                SignalType.ON_SUBSCRIBE, SignalType.ON_COMPLETE)
                        .log(start.toString(),
                                Level.INFO,
                                SignalType.ON_SUBSCRIBE, SignalType.ON_COMPLETE)
                        .log(end.toString(),
                                Level.INFO,
                                SignalType.ON_SUBSCRIBE, SignalType.ON_COMPLETE)
                        .log("Service -> getStats return",
                                Level.INFO,
                                SignalType.ON_NEXT);
            }
        }
    }

    private List<ViewStats> getViewStatsUnique(List<String> uris, List<EndpointHit> endpointHits) {
        List<ViewStats> list;
        if (uris == null) {
            Set<String> uniqueUris = endpointHits.stream()
                    .map(EndpointHit::getUri).collect(Collectors.toSet());
            list = collectUniqueViewStats(new ArrayList<>(uniqueUris), endpointHits);
        } else {
            list = collectUniqueViewStats(uris, endpointHits);
        }
        return list;
    }

    private List<ViewStats> collectUniqueViewStats(List<String> uris, List<EndpointHit> endpointHits) {
        List<ViewStats> viewStatsList = new ArrayList<>();
        for (String uri : uris) {
            ViewStats viewStats = new ViewStats();
            viewStats.setApp("service");
            viewStats.setUri(uri);
            Set<String> uniqueIps = endpointHits.stream()
                    .filter(hit -> hit.getUri().equals(uri))
                    .map(EndpointHit::getIp)
                    .collect(Collectors.toSet());
            viewStats.setHits((long) uniqueIps.size());
            viewStatsList.add(viewStats);
        }
        return viewStatsList;
    }

    private List<ViewStats> getViewStats(List<String> uris, List<EndpointHit> endpointHits) {
        List<ViewStats> list;
        if (uris == null) {
            Set<String> uniqueUris = endpointHits.stream()
                    .map(EndpointHit::getUri).collect(Collectors.toSet());
            list = collectViewStats(new ArrayList<>(uniqueUris), endpointHits);
        } else {
            list = collectViewStats(uris, endpointHits);
        }
        return list;
    }

    private List<ViewStats> collectViewStats(List<String> uris, List<EndpointHit> endpointHits) {
        List<ViewStats> viewStatsList = new ArrayList<>();
        for (String uri : uris) {
            ViewStats viewStats = new ViewStats();
            viewStats.setApp("service");
            viewStats.setUri(uri);
            viewStats.setHits(endpointHits.stream()
                    .filter(hit -> hit.getUri().equals(uri))
                    .count());
            viewStatsList.add(viewStats);
        }
        return viewStatsList;
    }
}