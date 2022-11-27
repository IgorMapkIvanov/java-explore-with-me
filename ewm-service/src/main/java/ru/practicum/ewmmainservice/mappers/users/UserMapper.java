package ru.practicum.ewmmainservice.mappers.users;

import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.dto.users.UserDto;
import ru.practicum.ewmmainservice.models.User;

@Component
public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName());
    }
}