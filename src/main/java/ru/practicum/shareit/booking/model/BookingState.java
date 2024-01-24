package ru.practicum.shareit.booking.model;

public enum BookingState {

    ALL("Все", "ALL"),
    PAST("Завершённые", "PAST"),
    FUTURE("Будущие", "FUTURE"),
    CURRENT("Текущие", "CURRENT"),
    WAITING("Новое бронирование, ожидает одобрения", "WAITING"),
    APPROVED("Бронирование подтверждено владельцем", "APPROVED"),
    REJECTED("Бронирование отклонено владельцем", "REJECTED"),
    CANCELED("Бронирование отменено создателем", "CANCELED");

    private final String description;
    private final String code;


    BookingState(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }
}
