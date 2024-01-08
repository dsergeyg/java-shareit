package ru.practicum.shareit.booking.model;

public enum BookingState {
    WAITING ("Новое бронирование, ожидает одобрения"),
    APPROVED ("Бронирование подтверждено владельцем"),
    REJECTED ("Бронирование отклонено владельцем"),
    CANCELED ("Бронирование отменено создателем");

    private final String state;


    BookingState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
