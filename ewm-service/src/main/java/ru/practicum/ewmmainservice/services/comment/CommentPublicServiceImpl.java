package ru.practicum.ewmmainservice.services.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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
public class CommentPublicServiceImpl implements CommentPublicService {
    private final CommentRepository commentRepository;

    @Override
    public List<CommentDto> getEventComments(Long eventId, Integer from, Integer size) {
        log.info("COMMENT_PUBLIC_SERVICE: Gel all comment for event with ID = {}; from - {}, size - {}",
                eventId, from, size);
        List<Comment> commentList = commentRepository.findByEventId(eventId,
                EwmPageable.of(from, size, Sort.by(Sort.Direction.DESC, "dateCreate")));
        return commentList.stream().map(CommentMapper::toCommentsDto).collect(Collectors.toUnmodifiableList());
    }
}