package ru.practicum.ewmmainservice.models;

import lombok.*;
import org.hibernate.Hibernate;
import ru.practicum.ewmmainservice.dto.locations.Location;
import ru.practicum.ewmmainservice.enums.State;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "annotation", length = 2000)
    private String annotation;
    @ManyToOne()
    @JoinColumn(name = "categories_id")
    private Category categories;
    @Builder.Default
    private Long confirmedRequests = 0L;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "description", length = 7000)
    private String description;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    @Embedded
    private Location location;
    @Column(name = "paid")
    private Boolean paid;
    @Builder.Default
    @Column(name = "participant_limit")
    private Integer participantLimit = 0;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    @Builder.Default
    private Boolean requestModeration = true;
    @Column(name = "state")
    @Enumerated
    private State state;
    @Column(name = "title")
    private String title;

    public void incrementConfirmedRequests() {
        confirmedRequests++;
    }

    public void decrementConfirmedRequests() {
        confirmedRequests--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Event event = (Event) o;
        return id != null && Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}