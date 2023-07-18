package ru.practicum.ewmmainservice.controllers.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.comment.CommentDto;
import ru.practicum.ewmmainservice.services.comment.CommentPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/comments")
public class CommentPublicController {
    private final CommentPublicService commentPublicService;

    @GetMapping("/{eventId}")
    public List<CommentDto> getEventComments(@PathVariable @Positive Long eventId,
                                             @RequestParam(required = false, defaultValue = "0")
                                             @PositiveOrZero Integer from,
                                             @RequestParam(required = false, defaultValue = "10")
                                             @Positive Integer size) {
        log.info("COMMENT_PUBLIC_SERVICE: Gel all comment for event with ID = {}; from - {}, size - {}",
                eventId, from, size);
        return commentPublicService.getEventComments(eventId, from, size);
    }
}