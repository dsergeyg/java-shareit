package ru.practicum.shareit.booking.dto.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoIn;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {
    public static BookingDto toBookingDto(Booking booking, ItemDto itemDto, UserDto userDto) {
        return new BookingDto(booking.getId(),
                itemDto,
                userDto,
                booking.getStart(),
                booking.getEnd(),
                booking.getStatus());
    }

    public static Booking toBooking(BookingDto bookingDto, User booker, Item item, BookingState bookingState) {
        return new Booking(bookingDto.getId(),
                booker,
                item,
                bookingDto.getStart(),
                bookingDto.getEnd(),
                bookingState);
    }

    public static Booking toBooking(BookingDtoIn bookingDto, User booker, Item item, BookingState bookingState) {
        return new Booking(null,
                booker,
                item,
                bookingDto.getStart(),
                bookingDto.getEnd(),
                bookingState);
    }
}
