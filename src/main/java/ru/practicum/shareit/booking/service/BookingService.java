package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoIn;

import java.util.List;

public interface BookingService {

    BookingDto addBooking(long userId, BookingDtoIn bookingDto);

    BookingDto cancelBooking(long userId, long bookingId, boolean canceled);

    BookingDto approvedBooking(long userId, long bookingId, boolean approved);

    BookingDto getBooking(long userId, long bookingId);

    List<BookingDto> getBookingItemByState(long userId, String state);

    List<BookingDto> getBookingItemOwnerByState(long userId, String state);
}
