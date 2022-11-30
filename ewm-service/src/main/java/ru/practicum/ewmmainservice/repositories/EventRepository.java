package ru.practicum.ewmmainservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewmmainservice.models.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByInitiatorId(Long userId, Pageable pageable);

    List<Event> findByCategoryId(Long catId, Pageable pageable);

    @Query("select event from Event event where (:users is null or event.initiator.id in :users) " +
            "and (:states is null or event.state in :states) " +
            "and (:categories is null or event.category.id in :categories) " +
            "and (event.eventDate > :rangeStart and event.eventDate < :rangeEnd)")
    List<Event> searchAllEventsForAdmin(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, Pageable pageable);
}