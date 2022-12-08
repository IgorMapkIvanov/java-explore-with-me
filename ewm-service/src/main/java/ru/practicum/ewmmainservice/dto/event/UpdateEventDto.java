package ru.practicum.ewmmainservice.dto.event;

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