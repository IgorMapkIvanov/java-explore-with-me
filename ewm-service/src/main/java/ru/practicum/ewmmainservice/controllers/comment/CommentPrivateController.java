package ru.practicum.ewmmainservice.controllers.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.comment.CommentDto;
import ru.practicum.ewmmainservice.dto.comment.NewCommentDto;
import ru.practicum.ewmmainservice.services.comment.CommentPrivateService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}")
public class CommentPrivateController {
    private final CommentPrivateService commentPrivateService;

    @GetMapping(path = "/comments")
    public List<CommentDto> getAll(@PathVariable @Positive Long userId,
                                   @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer from,
                                   @RequestParam(required = false, defaultValue = "10") @Positive Integer size) {
        log.info("COMMENT_PRIVATE_CONTROLLER: Get comments with param: user ID = {}, from = {}, size = {}"
                , userId, from, size);
        return commentPrivateService.getComments(userId, from, size);
    }

    @PostMapping(path = "/event/{eventId}/comment")
    public CommentDto addComment(@PathVariable @Positive Long userId,
                                 @PathVariable @Positive Long eventId,
                                 @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("COMMENT_PRIVATE_CONTROLLER: Add comment: user ID = {}, new comment = {}"
                , userId, newCommentDto);
        return commentPrivateService.addComment(userId, eventId, newCommentDto);
    }

    @PatchMapping(path = "/comment/{commentId}")
    public CommentDto editComment(@PathVariable @Positive Long userId,
                                  @PathVariable @Positive Long commentId,
                                  @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("COMMENT_PRIVATE_CONTROLLER: Edit comment with ID = {}: user ID = {}, edit comment = {}"
                , commentId, userId, newCommentDto);
        return commentPrivateService.editComment(userId, commentId, newCommentDto);
    }

    @DeleteMapping("/comment/{commentId}")
    public void delete(@PathVariable @Positive Long userId, @PathVariable @Positive Long commentId) {
        log.info("COMMENT_PRIVATE_CONTROLLER: Delete comment: user ID = {}, comment ID = {}"
                , userId, commentId);
        commentPrivateService.deleteComment(userId, commentId);
    }
}