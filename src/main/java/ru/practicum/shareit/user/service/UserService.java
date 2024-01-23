package ru.practicum.shareit.user.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@Transactional(readOnly = true)
public interface UserService {
    @Transactional
    UserDto addUser(UserDto userDto);

    @Transactional
    UserDto updateUser(UserDto userDto, long userId);

    @Transactional(readOnly = true)
    List<UserDto> getUsers();

    @Transactional(readOnly = true)
    UserDto getUserById(long id);

    @Transactional
    void deleteUser(long userId);
}
