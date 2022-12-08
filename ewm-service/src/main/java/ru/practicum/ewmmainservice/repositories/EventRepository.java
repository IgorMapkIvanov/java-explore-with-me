package ru.practicum.ewmmainservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmmainservice.enums.State;
import ru.practicum.ewmmainservice.models.Event;
import ru.practicum.ewmmainservice.models.User;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    @Query("select e from Event e where e.categories = ?1")
    List<Event> findByCategory(Long catId);

    Optional<Event> findByIdAndInitiator(Long eventId, User user);

    Optional<Event> findByIdAndState(Long eventId, State state);

    List<Event> findAllByInitiator(User user);
}