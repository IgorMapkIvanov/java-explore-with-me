package ru.practicum.ewmmainservice.dto.categories;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Long id;
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