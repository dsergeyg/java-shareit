package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@Builder(setterPrefix = "set")
public class UserDto {
    @Email(message = "Email doesn't match email pattern, example \"my@yandex.ru\"")
    private String email;
    private String name;
}
