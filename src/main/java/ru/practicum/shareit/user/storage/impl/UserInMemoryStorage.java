package ru.practicum.shareit.user.storage.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Component("userInMemoryStorage")
public class UserInMemoryStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long idSequence;

    @Override
    public User addUserToStorage(User user) {
        user.setId(++idSequence);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUserInStorage(User user) {
        if (user.getEmail() != null)
            users.get(user.getId()).setEmail(user.getEmail());
        if (user.getName() != null)
            users.get(user.getId()).setName(user.getName());
        return users.get(user.getId());
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(long id) {
        return users.get(id);
    }

    @Override
    public void deleteUser(long userId) {
        users.remove(userId);
    }
}
