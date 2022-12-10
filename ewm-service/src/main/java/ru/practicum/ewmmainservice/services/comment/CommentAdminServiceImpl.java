package ru.practicum.ewmmainservice.services.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.dto.comment.CommentDto;
import ru.practicum.ewmmainservice.mappers.comment.CommentMapper;
import ru.practicum.ewmmainservice.models.Comment;
import ru.practicum.ewmmainservice.pageable.EwmPageable;
import ru.practicum.ewmmainservice.repositories.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentAdminServiceImpl implements CommentAdminService {
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public void delete(Long commentId) {
        log.info("COMMENT_ADMIN_SERVICE: Delete comment with ID = {}", commentId);
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getComments(Long userId, Long eventId, Integer from, Integer size) {
        log.info("COMMENT_ADMIN_SERVICE: Get all comments with param: user ID = {}, event ID = {}, " +
                "from - {}, size - {}", userId, eventId, from, size);
        List<Comment> commentList = commentRepository.findByAuthorIdAndEventId(userId, eventId,
                EwmPageable.of(from, size, Sort.by(Sort.Direction.DESC, "dateCreate")));
        return commentList.stream().map(CommentMapper::toCommentsDto).collect(Collectors.toUnmodifiableList());
    }
}