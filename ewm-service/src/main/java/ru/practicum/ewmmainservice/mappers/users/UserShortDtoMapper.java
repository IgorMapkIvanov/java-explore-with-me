package ru.practicum.ewmmainservice.mappers.users;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.dto.users.UserShortDto;
import ru.practicum.ewmmainservice.models.User;
import ru.practicum.ewmmainservice.repositories.UserRepository;

@Lazy
@Component
@RequiredArgsConstructor
public class UserShortDtoMapper {
    private final UserRepository repository;

    public UserShortDto toUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }

    public User toUser(UserShortDto dto) {
        return new User(dto.getId(), dto.getName(),
                repository.findById(dto.getId()).orElseThrow().getEmail());

    }
}