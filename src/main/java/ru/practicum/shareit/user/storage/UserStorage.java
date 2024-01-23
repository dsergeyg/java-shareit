package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Set;

public interface UserStorage {

    User addUserToStorage(User user);

    User updateUserInStorage(UserDto userDto, long userId);

    List<User> getUsers();

    User getUserById(long id);

    void deleteUser(long userId);

    Set<String> getEmails();
}
