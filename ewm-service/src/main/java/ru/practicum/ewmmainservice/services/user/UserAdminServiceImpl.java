package ru.practicum.ewmmainservice.services.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.dto.user.NewUserRequest;
import ru.practicum.ewmmainservice.dto.user.UserDto;
import ru.practicum.ewmmainservice.exceptions.ConflictException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.mappers.user.UserMapper;
import ru.practicum.ewmmainservice.models.User;
import ru.practicum.ewmmainservice.pageable.EwmPageable;
import ru.practicum.ewmmainservice.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAdminServiceImpl implements UserAdminService {
    private final UserRepository userRepository;

    /**
     * Get a list of users by ids.
     *
     * @param ids      {@link List} of {@link Long}
     * @param pageable {@link EwmPageable Pageable}
     * @return {@link List} of {@link UserDto}
     */
    @Override
    public List<UserDto> getUsers(List<Long> ids, Pageable pageable) {
        List<UserDto> userDtoList;
        if (!ids.isEmpty()) {
            userDtoList = userRepository.findAllByIds(ids, pageable).stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toUnmodifiableList());
            log.info("USER_ADMIN_SERVICE: Get users by IDs = {}.", ids);
            return userDtoList;
        }
        userDtoList = userRepository.findAll(pageable).stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toUnmodifiableList());
        log.info("USER_ADMIN_SERVICE: Get all users.");
        return userDtoList;
    }

    /**
     * Add new user.
     *
     * @param newUserRequest {@link NewUserRequest}
     * @return {@link UserDto}
     */
    @Override
    @Transactional
    public UserDto addUser(NewUserRequest newUserRequest) {
        log.info("USER_ADMIN_SERVICE: Add new user : {}.", newUserRequest);
        try {
            User user = userRepository.save(UserMapper.toUser(newUserRequest));
            log.info("USER_ADMIN_SERVICE: User has been added, ID = {}.", user.getId());
            return UserMapper.toUserDto(user);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMessage(), "Conflict exception");
        }
    }

    /**
     * Delete user by ID.
     *
     * @param userId {@link Long}
     */
    @Override
    @Transactional
    public void deleteUser(Long userId) {
        checkUserInDb(userId);
        userRepository.deleteById(userId);
        log.info("USER_ADMIN_SERVICE: Delete user by ID = {}.", userId);
    }

    /**
     * Edit user.
     *
     * @param userDto {@link UserDto}
     */
    @Override
    @Transactional
    public UserDto editUser(UserDto userDto) {
        User user = checkUserInDb(userDto.getId());
        if (userDto.getName() != null && !userDto.getName().isEmpty()) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
            user.setEmail(userDto.getEmail());
        }
        log.info("USER_ADMIN_SERVICE: Edit user by ID = {}.", user.getId());
        return UserMapper.toUserDto(user);
    }

    /**
     * Check user in DB.
     *
     * @param id {@link Long}
     */
    private User checkUserInDb(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    String message = String.format("User with ID = %s not found", id);
                    String reason = "User not found";
                    throw new NotFoundException(message, reason);
                });
    }
}