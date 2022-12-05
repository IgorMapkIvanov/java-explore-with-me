package ru.practicum.ewmmainservice.dto.users;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserShortDto {
    @NotNull
    @Positive
    private Long id;
    @NotNull
    @NotBlank
    private String name;
}