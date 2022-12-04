package ru.practicum.ewmmainservice.dto.events;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UpdateEventDto extends EventBaseDto {
    private Long eventId;
}