package ru.practicum.ewmmainservice.controllers.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.comment.CommentDto;
import ru.practicum.ewmmainservice.services.comment.CommentAdminService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/comments")
public class CommentAdminController {
    private final CommentAdminService commentAdminService;

    @GetMapping
    public List<CommentDto> getAll(@RequestParam(required = false) @Positive Long userId,
                                   @RequestParam(required = false) @Positive Long eventId,
                                   @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer from,
                                   @RequestParam(required = false, defaultValue = "10") @Positive Integer size) {
        log.info("COMMENT_ADMIN_CONTROLLER: Get all comments with param: user ID = {}, event ID = {}, " +
                "from - {}, size - {}", userId, eventId, from, size);
        return commentAdminService.getComments(userId, eventId, from, size);
    }

    @DeleteMapping("/comment/{commentId}")
    public void delete(@PathVariable Long commentId) {
        log.info("COMMENT_ADMIN_CONTROLLER: Delete comment with ID = {}", commentId);
        commentAdminService.delete(commentId);
    }
}