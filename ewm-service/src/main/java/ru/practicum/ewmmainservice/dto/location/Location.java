package ru.practicum.ewmmainservice.dto.location;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Location {
    private Float lat;
    private Float lon;
    private String descLocation;
}
