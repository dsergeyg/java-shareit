package ru.practicum.shareit.exception;

import javax.validation.ValidationException;

public class NotEnoughData extends ValidationException {
    public NotEnoughData(String message) {
        super(message);
    }
}
