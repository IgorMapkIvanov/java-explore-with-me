package ru.practicum.ewmmainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.ewmmainservice.models.Event;
import ru.practicum.ewmmainservice.models.Request;
import ru.practicum.ewmmainservice.models.User;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request> {
    List<Request> findAllByRequester(User user);

    Optional<Request> findByIdAndRequester(Long id, User user);

    Optional<Request> findByRequesterAndEvent(User user, Event event);
}