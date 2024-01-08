package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {

    User addUser(UserDto userDto);

    User updateUser(UserDto userDto, long userId);

    List<User> getUsers();

    User getUserById(long id);

    void deleteUser(long userId);
}
