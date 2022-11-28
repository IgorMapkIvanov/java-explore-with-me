package ru.practicum.ewmmainservice.services.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.dto.users.NewUserRequest;
import ru.practicum.ewmmainservice.dto.users.UserDto;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.mappers.users.UserMapper;
import ru.practicum.ewmmainservice.models.User;
import ru.practicum.ewmmainservice.pageable.EwnPageable;
import ru.practicum.ewmmainservice.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService {

    private final UserRepository userRepository;

    /**
     * Get a list of users by ids.
     *
     * @param ids  {@link List} of {@link Long}
     * @param from {@link Integer}
     * @param size {@link Integer}
     * @return {@link List} of {@link UserDto}
     */
    @Override
    @Transactional
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        Pageable pageable = EwnPageable.of(from, size);
        List<UserDto> dtoList;
        if (!ids.isEmpty()) {
            dtoList = userRepository.findAllByIds(ids, pageable).stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toUnmodifiableList());
            log.info("USER_ADMIN_SERVICE: Get users by IDs = {}.", ids);
            return dtoList;
        }
        dtoList = userRepository.findAll(pageable).stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toUnmodifiableList());
        log.info("USER_ADMIN_SERVICE: Get all users.");
        return dtoList;
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
        UserDto addedUser = UserMapper.toUserDto(userRepository.save(UserMapper.toUser(newUserRequest)));
        log.info("USER_ADMIN_SERVICE: User has been added, ID = {}.", addedUser.getId());
        return addedUser;
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
     * Check user in DB.
     *
     * @param id {@link Long}
     */
    private void checkUserInDb(Long id) {
        if (!userRepository.existsById(id)) {
            log.info("USER_ADMIN_SERVICE: User with ID = {}, not found!", id);
            throw new NotFoundException(String.format("User with id = '%s' not found!", id));
        }
    }

    /**
     * Get user by ID.
     *
     * @param id {@link Long}
     * @return {@link User}
     */
    @Override
    public User getUserById(Long id) {
        log.info("USER_ADMIN_SERVICE: Get user by ID = {}", id);
        return userRepository.findById(id).orElseThrow(()-> {
            log.info("USER_ADMIN_SERVICE: User with ID = {}, not found!", id);
            throw new NotFoundException(String.format("User with id = '%s' not found!", id));
        });
    }
}