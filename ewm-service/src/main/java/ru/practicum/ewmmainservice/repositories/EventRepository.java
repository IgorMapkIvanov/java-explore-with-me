package ru.practicum.ewmmainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.ewmmainservice.enums.EventState;
import ru.practicum.ewmmainservice.models.Event;
import ru.practicum.ewmmainservice.models.User;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    Optional<Event> findByIdAndInitiator(Long eventId, User user);

    Optional<Event> findByIdAndState(Long eventId, EventState eventState);

    List<Event> findAllByInitiator(User user);
}