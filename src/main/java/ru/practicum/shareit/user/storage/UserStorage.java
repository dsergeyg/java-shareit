package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserStorage {

    User addUserToStorage(User user);

    User updateUserInStorage(User user);

    List<User> getUsers();

    User getUserById(long id);

    void deleteUser(long userId);
}
