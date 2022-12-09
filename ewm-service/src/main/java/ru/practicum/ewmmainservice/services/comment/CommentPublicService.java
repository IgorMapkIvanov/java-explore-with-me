package ru.practicum.ewmmainservice.services.comment;

import ru.practicum.ewmmainservice.dto.comment.CommentDto;

import java.util.List;

public interface CommentPublicService {
    List<CommentDto> getEventComments(Long eventId, Integer from, Integer size);
}