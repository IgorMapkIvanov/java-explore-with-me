package ru.practicum.ewmmainservice.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import ru.practicum.ewmmainservice.dto.categories.CategoryDto;
import ru.practicum.ewmmainservice.dto.locations.Location;
import ru.practicum.ewmmainservice.dto.users.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDto {
    private Long id;
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
    private CategoryDto category;
    @NotNull
    @NotBlank
    private Boolean paid;
    @NotNull
    @NotBlank
    private UserShortDto initiator;
    private Long confirmedRequests;
    private Long views;

    @Override
    public String toString() {
        return "class EventShortDto {\n" +
                "    id:                " + id + "\n" +
                "    title:             " + title + "\n" +
                "    annotation:        " + annotation + "\n" +
                "    description:       " + description + "\n" +
                "    eventDate:         " + eventDate + "\n" +
                "    location:          " + location + "\n" +
                "    category:          " + category + "\n" +
                "    paid:              " + paid + "\n" +
                "    initiator:         " + initiator + "\n" +
                "    confirmedRequests: " + confirmedRequests + "\n" +
                "    views:             " + views + "\n" +
                "}";
    }
}