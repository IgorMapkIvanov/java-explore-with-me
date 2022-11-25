package ru.practicum.ewmmainservice.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import ru.practicum.ewmmainservice.dto.locations.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String annotation;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    @NotBlank
    private String eventDate;
    @NotNull
    @NotBlank
    private Location location;
    @NotNull
    @NotBlank
    private Long category;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;

    @Override
    public String toString() {
        return "class NewEventDto {\n" +
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