package ru.practicum.ewmmainservice.client;

import org.springframework.http.ResponseEntity;
import ru.practicum.ewmmainservice.client.statistic.EndpointHit;
import ru.practicum.ewmmainservice.client.statistic.ViewHits;

import java.util.List;

public interface EventsClient {

    void saveStat(EndpointHit endpointHit);

    ResponseEntity<ViewHits[]> getStat(List<String> uris);
}