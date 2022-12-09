package ru.practicum.ewmmainservice.mappers.comment;

import ru.practicum.ewmmainservice.dto.comment.CommentDto;
import ru.practicum.ewmmainservice.models.Comment;

public class CommentMapper {
    public static CommentDto toCommentsDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .authorId(comment.getAuthor().getId())
                .eventId(comment.getEvent().getId())
                .dateCreate(comment.getDateCreate())
                .dateEdit(comment.getDateEdit())
                .text(comment.getText())
                .edited(comment.getEdited())
                .build();
    }
}