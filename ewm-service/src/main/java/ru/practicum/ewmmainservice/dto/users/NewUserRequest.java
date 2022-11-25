package ru.practicum.ewmmainservice.dto.users;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequest {
    @NotNull
    @NotBlank
    private String name;
    @Email
    @NotNull
    @NotBlank
    private String email;

    @Override
    public String toString() {
        return "class NewUserRequest {\n" +
                "    name:  " + name + "\n" +
                "    email: " + email + "\n" +
                "}";
    }
}