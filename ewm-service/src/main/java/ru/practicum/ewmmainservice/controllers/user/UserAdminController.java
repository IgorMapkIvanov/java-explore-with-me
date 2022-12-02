package ru.practicum.ewmmainservice.controllers.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.dto.users.NewUserRequest;
import ru.practicum.ewmmainservice.dto.users.UserDto;
import ru.practicum.ewmmainservice.pageable.EwmPageable;
import ru.practicum.ewmmainservice.services.users.UserAdminService;

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
    private final UserAdminService userAdminServiceice;

    //GET requests
    @GetMapping
    public List<UserDto> getUsers(@RequestParam(name = "ids", required = false) List<Long> ids,
                                  @RequestParam(name = "from", required = false, defaultValue = "0") @PositiveOrZero int from,
                                  @RequestParam(name = "size", required = false, defaultValue = "10") @Positive int size) {
        Pageable pageable = EwmPageable.of(from, size);
        log.info("USER_ADMIN_CONTROLLER: Get users by IDs = {}.", ids);
        return userAdminServiceice.getUsers(ids, pageable);
    }

    //POST requests
    @PostMapping
    public UserDto addUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        log.info("USER_ADMIN_CONTROLLER: Add new user: {}.", newUserRequest);
        return userAdminServiceice.addUser(newUserRequest);
    }

    //PATCH requests
    @PatchMapping
    public UserDto edit(@Valid @RequestBody UserDto usersDto) {
        return userAdminServiceice.editUser(usersDto);
    }

    //DELETE requests
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable @Positive Long userId) {
        log.info("USER_ADMIN_CONTROLLER: Delete user with ID = {}.", userId);
        userAdminServiceice.deleteUser(userId);
    }
}