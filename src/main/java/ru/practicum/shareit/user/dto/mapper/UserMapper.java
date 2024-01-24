package ru.practicum.shareit.user.dto.mapper;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return UserDto.builder().setId(user.getId()).setName(user.getName())
                .setEmail(user.getEmail()).build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder().setId(userDto.getId()).setEmail(userDto.getEmail()).setName(userDto.getName()).build();
    }
}
