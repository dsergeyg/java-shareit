package ru.practicum.shareit.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.AlReadyExists;
import ru.practicum.shareit.exception.NotEnoughData;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;
    private final ItemStorage itemStorage;

    @Autowired
    public UserServiceImpl(@Qualifier("userInMemoryStorage") UserStorage userStorage,
                           @Qualifier("itemInMemoryStorage") ItemStorage itemStorage) {
        this.userStorage = userStorage;
        this.itemStorage = itemStorage;
    }

    @Override
    public User addUser(UserDto userDto) {
        if (userDto.getEmail() == null) {
            throw new NotEnoughData("Not enough data \"email\"");
        }
        if (checkUserEmail(userDto))
            throw new AlReadyExists("Email already exists");
        return userStorage.addUserToStorage(UserMapper.toUser(userDto));
    }

    @Override
    public User updateUser(UserDto userDto, long userId) {
        if (getUserById(userId) == null)
            throw new NotFoundException("User not found");
        if (userDto.getEmail() != null) {
            if (checkUserEmail(userDto, userId))
                throw new AlReadyExists("Email already exists");
        }
        return userStorage.updateUserInStorage(UserMapper.toUser(userDto, userId));
    }

    @Override
    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    @Override
    public User getUserById(long id) {
        return userStorage.getUserById(id);
    }

    @Override
    public void deleteUser(long userId) {
        if (!checkUserHistory(userId))
            userStorage.deleteUser(userId);
    }

    private boolean checkUserHistory(long userId) {
        return itemStorage.getItemByUser(userId).size() > 0;
    }

    private boolean checkUserEmail(UserDto userDto, long userId) {
        for (User user : userStorage.getUsers()) {
            if (user.getId() != userId && user.getEmail().equals(userDto.getEmail()))
                return true;
        }
        return false;
    }

    private boolean checkUserEmail(UserDto userDto) {
        for (User user : userStorage.getUsers()) {
            if (user.getEmail().equals(userDto.getEmail()))
                return true;
        }
        return false;
    }
}
