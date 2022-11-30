package ru.practicum.ewmmainservice.dto.compilations;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private Boolean pinned;
    @NotNull
    @NotBlank
    private Set<Long> events;

    @Override
    public String toString() {
        return "class NewCompilationDto {\n" +
                "    title:  " + title + "\n" +
                "    pinned: " + pinned + "\n" +
                "    events: " + events + "\n" +
                "}";
    }
}