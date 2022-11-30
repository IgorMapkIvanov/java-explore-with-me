package ru.practicum.ewmmainservice.services.users;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.dto.users.NewUserRequest;
import ru.practicum.ewmmainservice.dto.users.UserDto;
import ru.practicum.ewmmainservice.models.User;

import java.util.List;

public interface UserAdminService {

    User getUserById(Long id);

    List<UserDto> getUsers(List<Long> ids, Pageable pageable);

    UserDto addUser(NewUserRequest newUserRequest);

    void deleteUser(Long userId);
}