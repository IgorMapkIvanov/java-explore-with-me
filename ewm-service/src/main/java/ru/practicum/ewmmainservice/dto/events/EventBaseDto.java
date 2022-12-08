package ru.practicum.ewmmainservice.dto.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.ewmmainservice.Utils.Constants.DATE_TIME_FORMAT;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class EventBaseDto {
    @Size(min = 20)
    @Size(max = 1000)
    @NotBlank
    protected String annotation;
    @NotNull
    protected Long category;
    @Size(min = 20)
    @Size(max = 5000)
    @NotBlank
    protected String description;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    protected LocalDateTime eventDate;
    @Builder.Default
    protected Boolean paid = false;
    @Builder.Default
    protected Integer participantLimit = 0;
    @Size(min = 3)
    @Size(max = 255)
    protected String title;
}