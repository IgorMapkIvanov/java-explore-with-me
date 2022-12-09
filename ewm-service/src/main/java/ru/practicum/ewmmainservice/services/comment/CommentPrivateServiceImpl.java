package ru.practicum.ewmmainservice.services.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.dto.comment.CommentDto;
import ru.practicum.ewmmainservice.dto.comment.NewCommentDto;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.mappers.comment.CommentMapper;
import ru.practicum.ewmmainservice.models.Comment;
import ru.practicum.ewmmainservice.models.Event;
import ru.practicum.ewmmainservice.models.User;
import ru.practicum.ewmmainservice.pageable.EwmPageable;
import ru.practicum.ewmmainservice.repositories.CommentRepository;
import ru.practicum.ewmmainservice.repositories.EventRepository;
import ru.practicum.ewmmainservice.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentPrivateServiceImpl implements CommentPrivateService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        log.info("COMMENT_PRIVATE_SERVICE: Add comment: user ID = {}, event ID = {}, comment text = {}"
                , userId, eventId, newCommentDto);
        User author = userRepository.findById(userId)
                .orElseThrow(() -> {
                    String message = String.format("User with ID = %s not found.", userId);
                    String reason = "User not found";
                    throw new NotFoundException(message, reason);
                });
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    String message = String.format("Event with ID = %s not found.", eventId);
                    String reason = "Event not found";
                    throw new NotFoundException(message, reason);
                });
        Comment comment = Comment.builder()
                .author(author)
                .event(event)
                .dateCreate(LocalDateTime.now())
                .text(newCommentDto.getText())
                .build();
        return CommentMapper.toCommentsDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public CommentDto editComment(Long userId, Long commentId, NewCommentDto newCommentDto) {
        log.info("COMMENT_PRIVATE_CONTROLLER: Edit comment with ID = {}: user ID = {}, edit comment = {}"
                , commentId, userId, newCommentDto);
        Comment comment = commentRepository.findByIdAndAuthorId(commentId, userId)
                .orElseThrow(() -> {
                    String message = String.format("Comment with ID = %s not found.", commentId);
                    String reason = "Comment not found";
                    throw new NotFoundException(message, reason);
                });
        comment.setEdited(true);
        comment.setText(newCommentDto.getText());
        comment.setDateEdit(LocalDateTime.now());
        return CommentMapper.toCommentsDto(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        log.info("COMMENT_PRIVATE_SERVICE: Delete comment: user ID = {}, comment ID = {}"
                , userId, commentId);
        commentRepository.deleteByAuthorAndId(User.builder().id(userId).build(), commentId);
    }

    @Override
    public List<CommentDto> getComments(Long userId, Integer from, Integer size) {
        log.info("COMMENT_PRIVATE_SERVICE: Get comments with param: user ID = {}, from = {}, size = {}"
                , userId, from, size);
        List<Comment> commentList = commentRepository.findByAuthorId(userId,
                EwmPageable.of(from, size, Sort.by(Sort.Direction.DESC, "dateCreate")));
        return commentList.stream().map(CommentMapper::toCommentsDto).collect(Collectors.toUnmodifiableList());
    }
}