package ru.practicim.ewmstatsserver.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.practicim.ewmstatsserver.dto.EndpointHitDto;
import ru.practicim.ewmstatsserver.model.ViewStats;
import ru.practicim.ewmstatsserver.service.StatsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@WebFluxTest(StatsController.class)
class StatsControllerTest {

    @MockBean
    private StatsService service;

    @Autowired
    private WebTestClient webClient;

    @Test
    void shouldAddEndpointIsOk() {
        EndpointHitDto endpointHitDto = new EndpointHitDto();
        endpointHitDto.setId(1L);
        endpointHitDto.setApp("service");
        endpointHitDto.setUri("/hit");
        endpointHitDto.setIp("1:1:1");
        endpointHitDto.setTimestamp("2022-09-06 11:00:26");

        Mockito.when(service.addEndpoint(Mockito.any())).thenReturn(Mono.just(endpointHitDto));

        webClient.post()
                .uri("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(endpointHitDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EndpointHitDto.class);

        Mockito.verify(service, Mockito.times(1)).addEndpoint(Mockito.any());
    }

    @Test
    void shouldGetStatsWhenIPNotUnique() {
        List<ViewStats> statsList = new ArrayList<>();
        ViewStats viewStats1 = new ViewStats("service", "/stats", 3L);
        ViewStats viewStats2 = new ViewStats("service", "/hit", 5L);
        statsList.add(viewStats1);
        statsList.add(viewStats2);

        Mockito.when(service.getStats(Mockito.any(LocalDateTime.class),
                Mockito.any(LocalDateTime.class),
                Mockito.any(), Mockito.anyBoolean())).thenReturn(Mono.just(statsList));

        webClient.get()
                .uri("/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                        "2022-09-06 11:00:26",
                        "2022-09-06 11:00:27",
                        "/stats,/hit",
                        "false")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ViewStats.class);

        Mockito.verify(service, Mockito.times(1)).getStats(Mockito.any(LocalDateTime.class),
                Mockito.any(LocalDateTime.class),
                Mockito.any(), Mockito.anyBoolean());
    }

    @Test
    void shouldGetStatsWhenStartOrEndIsWrong() {
        webClient.get()
                .uri("/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                        "2022-09-0611:00:26",
                        "2022-09-0611:00:27",
                        "/stats,/hit",
                        "false")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().is4xxClientError();

        Mockito.verify(service, Mockito.times(0)).getStats(Mockito.any(LocalDateTime.class),
                Mockito.any(LocalDateTime.class),
                Mockito.any(), Mockito.anyBoolean());
    }
}