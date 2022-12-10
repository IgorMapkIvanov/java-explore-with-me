package ru.practicum.ewmmainservice.services.comment;

import ru.practicum.ewmmainservice.dto.comment.CommentDto;
import ru.practicum.ewmmainservice.dto.comment.NewCommentDto;

import java.util.List;

public interface CommentPrivateService {
    CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto);

    CommentDto editComment(Long userId, Long commentId, NewCommentDto newCommentDto);

    void deleteComment(Long userId, Long commentId);

    List<CommentDto> getComments(Long userId, Integer from, Integer size);
}