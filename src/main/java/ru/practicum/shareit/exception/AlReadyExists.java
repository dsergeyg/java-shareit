package ru.practicum.shareit.exception;

public class AlReadyExists extends RuntimeException {
    public AlReadyExists(String message) {
        super(message);
    }
}
