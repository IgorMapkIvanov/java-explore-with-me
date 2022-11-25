package ru.practicum.ewmmainservice.dto.users;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull
    @NotBlank
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @Email
    @NotNull
    @NotBlank
    private String email;

    @Override
    public String toString() {
        return "class UserDto {\n" +
                "    id:    " + id + "\n" +
                "    name:  " + name + "\n" +
                "    email: " + email + "\n" +
                "}";
    }
}