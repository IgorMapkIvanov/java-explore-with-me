package ru.practicum.ewmmainservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmmainservice.client.statistic.EndpointHit;
import ru.practicum.ewmmainservice.client.statistic.ViewHits;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EventsClientImpl implements EventsClient {

    @Value(value = "${stats-server.url}")
    private String baseUri;

    @Override
    public void saveStat(EndpointHit endpointHit) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<Object> requestEntity = new HttpEntity<>(endpointHit, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(
                baseUri + "/hit",
                HttpMethod.POST,
                requestEntity,
                Object.class
        );
    }

    @Override
    public ResponseEntity<ViewHits[]> getStat(List<String> uris) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);
        String start = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String end = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        start = URLEncoder.encode(start, StandardCharsets.UTF_8);
        end = URLEncoder.encode(end, StandardCharsets.UTF_8);
        StringBuilder urisBuilder = new StringBuilder();
        for (int i = 0; i < uris.size(); i++) {
            if (i < (uris.size() - 1)) {
                urisBuilder.append("uris").append("=").append(uris.get(i)).append("&");
            } else {
                urisBuilder.append("uris").append("=").append(uris.get(i));
            }
        }
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                baseUri + "/stats?start=" + start + "&end=" + end + "&" + urisBuilder + "&unique=true",
                HttpMethod.GET,
                requestEntity,
                ViewHits[].class
        );
    }
}