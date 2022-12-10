package ru.practicum.ewmmainservice.dto.comment;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCommentDto {
    @Size(max = 3000)
    private String text;
}