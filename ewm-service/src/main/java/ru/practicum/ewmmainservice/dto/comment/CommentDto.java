package ru.practicum.ewmmainservice.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

import static ru.practicum.ewmmainservice.Utils.Constants.DATE_TIME_FORMAT;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private Long authorId;
    private Long eventId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime dateCreate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime dateEdit;
    private String text;
    private Boolean edited;
}