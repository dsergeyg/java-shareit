package ru.practicum.shareit.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.ArrayList;
import java.util.List;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public UserDto addUser(UserDto userDto) {
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(userDto)));
    }

    @Transactional
    @Override
    public UserDto updateUser(UserDto userDto, long userId) {
        User curUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        if (userDto.getEmail() != null)
            curUser.setEmail(userDto.getEmail());
        if (userDto.getName() != null)
            curUser.setName(userDto.getName());
        return UserMapper.toUserDto(userRepository.save(curUser));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getUsers() {
        List<UserDto> curUserDtoList = new ArrayList<>();
        for (User curUser : userRepository.findAll()) {
            curUserDtoList.add(UserMapper.toUserDto(curUser));
        }
        return curUserDtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserById(long id) {
        return UserMapper.toUserDto(userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found")));
    }

    @Transactional
    @Override
    public void deleteUser(long userId) {
        if (!checkUserHistory(userId))
            userRepository.deleteById(userId);
    }

    private boolean checkUserHistory(long userId) {
        return itemRepository.findByUserId(userId).size() > 0;
    }

    private boolean checkUserEmail(UserDto userDto) {
        return userRepository.findAllEmails().contains(userDto.getEmail());
    }
}
