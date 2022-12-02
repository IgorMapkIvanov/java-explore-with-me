package ru.practicum.ewmmainservice.dto.compilations;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class NewCompilationDto {
    private Set<Long> events;
    @Builder.Default
    private Boolean pinned = false;
    @NotBlank
    private String title;
}