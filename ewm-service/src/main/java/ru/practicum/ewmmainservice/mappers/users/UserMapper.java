package ru.practicum.ewmmainservice.mappers.users;

import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.dto.users.NewUserRequest;
import ru.practicum.ewmmainservice.dto.users.UserDto;
import ru.practicum.ewmmainservice.models.User;

@Component
public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName());
    }

    public static User toUser(NewUserRequest newUserRequest) {
        return new User(0L, newUserRequest.getName(), newUserRequest.getEmail());
    }
}