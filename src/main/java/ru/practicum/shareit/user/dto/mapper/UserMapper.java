package ru.practicum.shareit.user.dto.mapper;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return UserDto.builder().setName(user.getName())
                .setEmail(user.getEmail()).build();
    }

    public static User toUser(UserDto userDto, long userId) {
        return User.builder().setId(userId).setEmail(userDto.getEmail()).setName(userDto.getName()).build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder().setEmail(userDto.getEmail()).setName(userDto.getName()).build();
    }
}
