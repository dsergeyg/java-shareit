package ru.practicum.shareit.booking.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoIn;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDto postBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                  @Valid @RequestBody BookingDtoIn bookingDto) {
        return bookingService.addBooking(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto patchBooking(@PathVariable(name = "bookingId") long id,
                                   @RequestParam(name = "approved") boolean approved,
                                   @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.approvedBooking(userId, id, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@PathVariable(name = "bookingId") long id,
                                     @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.getBooking(userId, id);
    }

    @GetMapping
    public List<BookingDto> getAllBookingByState(@RequestParam(name = "state", required = false, defaultValue = "ALL") String state,
                                                 @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.getBookingItemByState(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllBookingByCurOwnerAndState(@RequestParam(name = "state", required = false, defaultValue = "ALL") String state,
                                                            @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.getBookingItemOwnerByState(userId, state);
    }
}
