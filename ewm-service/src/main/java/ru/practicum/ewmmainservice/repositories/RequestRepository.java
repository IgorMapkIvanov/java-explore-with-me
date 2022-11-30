package ru.practicum.ewmmainservice.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmainservice.enums.Status;
import ru.practicum.ewmmainservice.models.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByEventId(Long eventId, Pageable pageable);

    List<Request> findByRequesterId(Long requesterId, Pageable pageable);

    Request findByEventIdAndRequesterId(Long eventId, Long requesterId);

    Long countByEventIdAndStatus(Long eventId, Status status);

    List<Request> findByEventIdAndStatus(Long eventId, Status status);

    Boolean existsByEventIdAndStatus(Long eventId, Status status);
}