package ru.practicum.ewmmainservice.controllers.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.requests.ParticipationRequestDto;
import ru.practicum.ewmmainservice.services.requests.RequestPrivateService;

import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}/requests")
public class RequestPrivateController {
    private final RequestPrivateService service;

    @GetMapping
    public List<ParticipationRequestDto> getUserRequests(@PathVariable @Positive Long userId) {
        log.info("REQUEST_PRIVATE_CONTROLLER: Get user with ID = {} requests.", userId);
        return service.getUserRequests(userId);
    }

    @PostMapping
    public ParticipationRequestDto addRequest(@PathVariable @Positive Long userId,
                                              @RequestParam(name = "eventId") @Positive Long eventId) {
        log.info("REQUEST_PRIVATE_CONTROLLER: Add request: user ID = {}, event ID = {}.", userId, eventId);
        return service.addRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable @Positive Long userId,
                                                 @PathVariable @Positive Long requestId) {
        log.info("REQUEST_PRIVATE_CONTROLLER: Cancel request: user ID = {}, request ID = {}.", userId, requestId);
        return service.cancelRequest(userId, requestId);
    }
}