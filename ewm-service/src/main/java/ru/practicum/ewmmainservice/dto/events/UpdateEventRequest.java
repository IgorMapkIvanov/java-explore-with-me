package ru.practicum.ewmmainservice.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventRequest {
    @NotNull
    @Positive
    private Long eventId;
    private String title;
    private String annotation;
    private String description;
    private String eventDate;
    private Long category;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;

    @Override
    public String toString() {
        return "class UpdateEventRequest {\n" +
                "    eventId:           " + eventId + "\n" +
                "    title:             " + title + "\n" +
                "    annotation:        " + annotation + "\n" +
                "    description:       " + description + "\n" +
                "    eventDate:         " + eventDate + "\n" +
                "    category:          " + category + "\n" +
                "    paid:              " + paid + "\n" +
                "    participantLimit:  " + participantLimit + "\n" +
                "    requestModeration: " + requestModeration + "\n" +
                "}";
    }
}