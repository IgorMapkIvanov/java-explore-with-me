package ru.practicum.ewmmainservice.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.ewmmainservice.models.Comment;
import ru.practicum.ewmmainservice.models.User;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
    void deleteByAuthorAndId(User author, Long id);

    List<Comment> findByEventId(Long eventId, Pageable pageable);

    Optional<Comment> findByIdAndAuthorId(Long commentId, Long authorId);

    List<Comment> findByAuthorIdAndEventId(Long authorId, Long eventId, Pageable pageable);

    List<Comment> findByAuthorId(Long authorId, Pageable pageable);

}