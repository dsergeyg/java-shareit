package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
public class User {
    @NotNull
    private Long id;
    @Email(message = "Email doesn't match email pattern, example \"my@yandex.ru\"")
    @NotNull(message = "Email may not be empty")
    @Size(max = 100, message = "Email max string value 100 chars")
    private String email;
    @Size(max = 100, message = "Name max string value 100 chars")
    private String name;
}
