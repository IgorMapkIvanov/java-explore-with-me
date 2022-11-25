package ru.practicum.ewmmainservice.dto.categories;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {
    @NotNull
    @NotBlank
    private String name;

    @Override
    public String toString() {
        return "class NewCategoryDto {\n" +
                "    name: " + name + "\n" +
                "}";
    }
}