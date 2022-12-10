package ru.practicum.ewmmainservice.services.comment;

import ru.practicum.ewmmainservice.dto.comment.CommentDto;

import java.util.List;

public interface CommentAdminService {
    void delete(Long commentId);

    List<CommentDto> getComments(Long userId, Long eventId, Integer from, Integer size);
}