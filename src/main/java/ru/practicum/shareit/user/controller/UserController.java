package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto postUser(@Valid @RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto putUser(@Valid @RequestBody UserDto userDto,
                           @PathVariable long userId) {
        return userService.updateUser(userDto, userId);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }
}
