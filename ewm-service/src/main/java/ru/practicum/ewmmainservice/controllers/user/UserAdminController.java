package ru.practicum.ewmmainservice.controllers.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.users.NewUserRequest;
import ru.practicum.ewmmainservice.dto.users.UserDto;
import ru.practicum.ewmmainservice.services.users.UserAdminService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class UserAdminController {
    private final UserAdminService service;

    //GET requests
    @GetMapping("/admin/users")
    public List<UserDto> getUsers(@RequestParam(name = "ids", required = false) List<Long> ids,
                                  @RequestParam(name = "from", required = false, defaultValue = "0") @PositiveOrZero int from,
                                  @RequestParam(name = "size", required = false, defaultValue = "10") @Positive int size) {
        log.info("USER_ADMIN_CONTROLLER: Get users by IDs = {}.", ids);
        return service.getUsers(ids, from, size);
    }

    //POST requests
    @PostMapping("/admin/users")
    public UserDto addUser(@RequestBody NewUserRequest newUserRequest) {
        log.info("USER_ADMIN_CONTROLLER: Add new user: {}.", newUserRequest);
        return service.addUser(newUserRequest);
    }

    //DELETE requests
    @DeleteMapping("/admin/users/{userId}")
    public void deleteUser(@PathVariable @Positive Long userId) {
        log.info("USER_ADMIN_CONTROLLER: Delete user with ID = {}.", userId);
        service.deleteUser(userId);
    }
}