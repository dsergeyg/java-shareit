package ru.practicum.shareit.user.storage.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.*;

@Repository
@Component("userInMemoryStorage")
public class UserInMemoryStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> emails = new HashSet<>();
    private long idSequence;

    @Override
    public User addUserToStorage(User user) {
        user.setId(++idSequence);
        users.put(user.getId(), user);
        emails.add(user.getEmail());
        return user;
    }

    @Override
    public User updateUserInStorage(UserDto userDto, long userId) {
        User curUser = users.get(userId);
        if (userDto.getEmail() != null) {
            String email = curUser.getEmail();
            emails.remove(email);
            email = userDto.getEmail();
            curUser.setEmail(email);
            emails.add(email);
        }
        if (userDto.getName() != null)
            curUser.setName(userDto.getName());
        return users.get(userId);
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
        emails.remove(users.get(userId).getEmail());
        users.remove(userId);
    }

    @Override
    public Set<String> getEmails() {
        return emails;
    }
}
