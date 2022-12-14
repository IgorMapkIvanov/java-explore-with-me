package ru.practicum.ewmmainservice.controllers.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.user.NewUserRequest;
import ru.practicum.ewmmainservice.dto.user.UserDto;
import ru.practicum.ewmmainservice.pageable.EwmPageable;
import ru.practicum.ewmmainservice.services.user.UserAdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/users")
public class UserAdminController {
    private final UserAdminService userAdminService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(name = "ids", required = false) List<Long> ids,
                                  @RequestParam(name = "from", required = false, defaultValue = "0")
                                  @PositiveOrZero int from,
                                  @RequestParam(name = "size", required = false, defaultValue = "10")
                                  @Positive int size) {
        Pageable pageable = EwmPageable.of(from, size);
        log.info("USER_ADMIN_CONTROLLER: Get users by IDs = {}.", ids);
        return userAdminService.getUsers(ids, pageable);
    }

    @PostMapping
    public UserDto addUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        log.info("USER_ADMIN_CONTROLLER: Add new user: {}.", newUserRequest);
        return userAdminService.addUser(newUserRequest);
    }

    @PatchMapping
    public UserDto edit(@Valid @RequestBody UserDto usersDto) {
        return userAdminService.editUser(usersDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable @Positive Long userId) {
        log.info("USER_ADMIN_CONTROLLER: Delete user with ID = {}.", userId);
        userAdminService.deleteUser(userId);
    }
}