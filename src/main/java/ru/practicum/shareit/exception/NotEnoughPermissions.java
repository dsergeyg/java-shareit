package ru.practicum.shareit.exception;

public class NotEnoughPermissions extends NotFoundException {
    public NotEnoughPermissions(String message) {
        super(message);
    }
}
