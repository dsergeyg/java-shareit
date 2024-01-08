package ru.practicum.shareit.booking.dto.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {
    public static BookingDto toBookingDto(Booking booking) {
        return new BookingDto(booking.getId(),
                booking.getBooker().getId(),
                booking.getItem().getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getStatus());
    }

    public static Booking toBooking(BookingDto bookingDto, User booker, Item item) {
        return new Booking(bookingDto.getId(),
                booker,
                item,
                bookingDto.getStart(),
                bookingDto.getEnd(),
                bookingDto.getStatus());
    }
}
