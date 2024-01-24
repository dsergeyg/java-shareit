package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto addUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, long userId);

    List<UserDto> getUsers();

    UserDto getUserById(long id);

    void deleteUser(long userId);
}
