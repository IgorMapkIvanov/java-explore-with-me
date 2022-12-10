package ru.practicim.ewmstatsserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicim.ewmstatsserver.dto.EndpointHitDto;
import ru.practicim.ewmstatsserver.dto.ViewStatsDto;
import ru.practicim.ewmstatsserver.mapper.StatsMapper;
import ru.practicim.ewmstatsserver.repository.StatsRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static Utils.Constants.DATE_TIME_FORMAT;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private StatsRepository statsRepository;

    @Override
    @Transactional
    public void addHit(EndpointHitDto endpointHitDto) {
        statsRepository.save(StatsMapper.toEndpointHit(endpointHitDto));
    }

    @Override
    public List<ViewStatsDto> getViewStats(String start, String end, List<String> uris, Boolean unique) {
        start = URLDecoder.decode(start, StandardCharsets.UTF_8);
        end = URLDecoder.decode(end, StandardCharsets.UTF_8);
        LocalDateTime rangeStart = LocalDateTime.parse(start, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        LocalDateTime rangeEnd = LocalDateTime.parse(end, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        if (unique) {
            return statsRepository.countTotalIpDistinct(rangeEnd, rangeEnd, uris);
        } else {
            return statsRepository.countTotalIp(rangeStart, rangeEnd, uris);
        }
    }
}