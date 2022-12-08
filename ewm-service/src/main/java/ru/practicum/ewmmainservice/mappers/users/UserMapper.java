package ru.practicum.ewmmainservice.mappers.users;

import ru.practicum.ewmmainservice.dto.users.NewUserRequest;
import ru.practicum.ewmmainservice.dto.users.UserDto;
import ru.practicum.ewmmainservice.models.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User toUser(NewUserRequest newUserRequest) {
        return User.builder()
                .id(0L)
                .name(newUserRequest.getName())
                .email(newUserRequest.getEmail())
                .build();
    }
}