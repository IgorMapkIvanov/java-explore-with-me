package ru.practicum.ewmmainservice.dto.categories;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @NotNull
    @Positive
    private Long id;
    @NotNull
    @NotBlank
    private String name;

    @Override
    public String toString() {
        return "class CategoryDto {\n" +
                "    id:   " + id + "\n" +
                "    name: " + name + "\n" +
                "}";
    }
}