package ru.practicum.shareit.booking.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoIn;

import java.util.List;

@Transactional(readOnly = true)
public interface BookingService {
    @Transactional
    BookingDto addBooking(long userId, BookingDtoIn bookingDto);

    @Transactional
    BookingDto cancelBooking(long userId, long bookingId, boolean canceled);

    @Transactional
    BookingDto approvedBooking(long userId, long bookingId, boolean approved);

    @Transactional(readOnly = true)
    BookingDto getBooking(long userId, long bookingId);

    @Transactional(readOnly = true)
    List<BookingDto> getBookingItemByState(long userId, String state);

    @Transactional(readOnly = true)
    List<BookingDto> getBookingItemOwnerByState(long userId, String state);
}
