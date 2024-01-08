package ru.practicum.shareit.user.dto.mapper;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(user.getName(),
                user.getEmail());
    }

    public static User toUser(UserDto userDto, long userId) {
        return new User(userId,
                userDto.getEmail(),
                userDto.getName());
    }

    public static User toUser(UserDto userDto) {
        return new User(null,
                userDto.getEmail(),
                userDto.getName());
    }
}
