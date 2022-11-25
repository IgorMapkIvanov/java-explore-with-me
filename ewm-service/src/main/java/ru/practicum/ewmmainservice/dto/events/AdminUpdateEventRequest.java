package ru.practicum.ewmmainservice.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import ru.practicum.ewmmainservice.dto.locations.Location;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateEventRequest {
    private String title;
    private String annotation;
    private String description;
    private String eventDate;
    private Location location;
    private Long category;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;

    @Override
    public String toString() {
        return "class AdminUpdateEventRequest {\n" +
                "    title:             " + title + "\n" +
                "    annotation:        " + annotation + "\n" +
                "    description:       " + description + "\n" +
                "    eventDate:         " + eventDate + "\n" +
                "    location:          " + location + "\n" +
                "    category:          " + category + "\n" +
                "    paid:              " + paid + "\n" +
                "    participantLimit:  " + participantLimit + "\n" +
                "    requestModeration: " + requestModeration + "\n" +
                "}";
    }
}