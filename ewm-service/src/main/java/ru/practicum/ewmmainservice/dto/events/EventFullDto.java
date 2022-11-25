package ru.practicum.ewmmainservice.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import ru.practicum.ewmmainservice.dto.categories.CategoryDto;
import ru.practicum.ewmmainservice.dto.locations.Location;
import ru.practicum.ewmmainservice.dto.users.UserShortDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {
    private Long id;
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String annotation;
    private String description;
    @NotNull
    @NotBlank
    private String eventDate;
    @NotNull
    @NotBlank
    private Location location;
    @NotNull
    @NotBlank
    private CategoryDto category;
    @NotNull
    @NotBlank
    private Boolean paid;
    private Long participantLimit;
    @NotNull
    @NotBlank
    private UserShortDto initiator;
    private String createdOn;
    private String publishedOn;
    private Boolean requestModeration;
    private String state;
    private Long confirmedRequests;
    private Long views;

    @Override
    public String toString() {
        return "class EventFullDto {\n" +
                "    id:                " + id + "\n" +
                "    title:             " + title + "\n" +
                "    annotation:        " + annotation + "\n" +
                "    description:       " + description + "\n" +
                "    eventDate:         " + eventDate + "\n" +
                "    location:          " + location + "\n" +
                "    category:          " + category + "\n" +
                "    paid:              " + paid + "\n" +
                "    participantLimit:  " + participantLimit + "\n" +
                "    initiator:         " + initiator + "\n" +
                "    createdOn:         " + createdOn + "\n" +
                "    publishedOn:       " + publishedOn + "\n" +
                "    requestModeration: " + requestModeration + "\n" +
                "    confirmedRequests: " + confirmedRequests + "\n" +
                "    views:             " + views + "\n" +
                "}";
    }
}