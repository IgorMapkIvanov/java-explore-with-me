package ru.practicum.ewmmainservice.dto.compilations;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import ru.practicum.ewmmainservice.dto.events.EventShortDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {
    @NotNull
    @Positive
    private Long id;
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private Boolean pinned;
    private List<EventShortDto> events;

    @Override
    public String toString() {
        return "class CompilationDto {\n" +
                "    id:     " + id + "\n" +
                "    title:  " + title + "\n" +
                "    pinned: " + pinned + "\n" +
                "    events: " + events + "\n" +
                "}";
    }
}