package ru.practicum.ewmmainservice.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "comments")
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @NotNull
    @Column(name = "date_create")
    private LocalDateTime dateCreate;
    @Column(name = "date_edit")
    private LocalDateTime dateEdit;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @Column(name = "text", length = 2000)
    @NotNull
    private String text;
    @Builder.Default
    @Column(name = "edited")
    private Boolean edited = false;
}