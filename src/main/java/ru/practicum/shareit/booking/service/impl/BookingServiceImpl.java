package ru.practicum.shareit.booking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoIn;
import ru.practicum.shareit.booking.dto.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.NotEnoughData;
import ru.practicum.shareit.exception.NotEnoughPermissions;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UnsupportedStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
@Component("bookingServiceImpl")
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;

    @Autowired
    public BookingServiceImpl(@Qualifier("bookingRepository") BookingRepository bookingRepository, UserService userService, ItemService itemService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    @Transactional
    @Override
    public BookingDto addBooking(long userId, BookingDtoIn bookingDtoIn) {
        User curUser = UserMapper.toUser(userService.getUserById(userId));
        Item curItem = itemService.getFullItemById(bookingDtoIn.getItemId());
        if (curItem.getOwner().getId() == userId)
            throw new NotFoundException("User is owner");
        if (checkBooking(bookingDtoIn.getStart(), bookingDtoIn.getItemId()))
            throw new NotEnoughData("Date already booked");
        if (bookingDtoIn.getStart().isAfter(bookingDtoIn.getEnd()) || bookingDtoIn.getStart().equals(bookingDtoIn.getEnd()))
            throw new NotEnoughData("Bad date data in request");
        if (curItem.getAvailable())
            return BookingMapper.toBookingDto(
                    bookingRepository.save(
                            BookingMapper.toBooking(bookingDtoIn, curUser, curItem, BookingState.WAITING)), itemService.getItemById(bookingDtoIn.getItemId(), userId), userService.getUserById(userId));
        else
            throw new NotEnoughData("Item not found or not available");
    }

    @Transactional
    @Override
    public BookingDto approvedBooking(long userId, long bookingId, boolean approved) {
        Booking curBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Booking not found"));
        if (itemService.getFullItemById(curBooking.getItem().getId()).getOwner().getId() != userId)
            throw new NotEnoughPermissions("User must be owner");
        if (curBooking.getStatus().getCode().equals(BookingState.APPROVED.getCode()))
            throw new NotEnoughData("Already approved on date");
        if (approved)
            curBooking.setStatus(BookingState.APPROVED);
        else
            curBooking.setStatus(BookingState.REJECTED);
        return BookingMapper.toBookingDto(bookingRepository.save(curBooking),
                itemService.getItemById(curBooking.getItem().getId(), userId),
                userService.getUserById(curBooking.getBooker().getId()));
    }

    @Transactional
    @Override
    public BookingDto cancelBooking(long userId, long bookingId, boolean canceled) {
        Booking curBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Booking not found"));
        if (curBooking.getBooker().getId() != userId)
            throw new NotEnoughPermissions("User must be booker");
        if (canceled)
            curBooking.setStatus(BookingState.CANCELED);
        return BookingMapper.toBookingDto(bookingRepository.save(curBooking),
                itemService.getItemById(curBooking.getItem().getId(), userId),
                userService.getUserById(curBooking.getBooker().getId()));
    }

    @Transactional(readOnly = true)
    @Override
    public BookingDto getBooking(long userId, long bookingId) {
        Booking curBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Booking not found"));
        if (curBooking.getBooker().getId() == userId || itemService.getFullItemById(curBooking.getItem().getId()).getOwner().getId() == userId)
            return BookingMapper.toBookingDto(curBooking,
                    itemService.getItemById(curBooking.getItem().getId(), userId),
                    userService.getUserById(curBooking.getBooker().getId()));
        else
            throw new NotEnoughPermissions("User must be booker or item owner");
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingDto> getBookingItemByState(long userId, String state) {
        userService.getUserById(userId);
        if (state.equals(BookingState.ALL.getCode()))
            return listToBookingDto(bookingRepository.findByBookerId(userId, Sort.by("start").descending()), userId);
        if (state.equals(BookingState.CURRENT.getCode()))
            return listToBookingDto(bookingRepository.findByBookerIdAndEndAfterAndStartBefore(userId, LocalDateTime.now(), LocalDateTime.now(), Sort.by("start").descending()), userId);
        if (state.equals(BookingState.PAST.getCode()))
            return listToBookingDto(bookingRepository.findByBookerIdAndEndIsBeforeAndStatus(userId, LocalDateTime.now(), BookingState.APPROVED, Sort.by("start").descending()), userId);
        if (state.equals(BookingState.FUTURE.getCode()))
            return listToBookingDto(bookingRepository.findFutureBookingItemByCurUser(userId), userId);
        if (state.equals(BookingState.WAITING.getCode()) || state.equals(BookingState.REJECTED.getCode()))
            return listToBookingDto(bookingRepository.findByBookerIdAndStatus(userId, BookingState.valueOf(state), Sort.by("start").descending()), userId);
        throw new UnsupportedStatus(state);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingDto> getBookingItemOwnerByState(long userId, String state) {
        userService.getUserById(userId);
        if (state.equals(BookingState.ALL.getCode()))
            return listToBookingDto(bookingRepository.findAllBookingItemByOwner(userId), userId);
        if (state.equals(BookingState.CURRENT.getCode()))
            return listToBookingDto(bookingRepository.findCurrentBookingItemByOwner(userId, LocalDateTime.now(), LocalDateTime.now()), userId);
        if (state.equals(BookingState.PAST.getCode()))
            return listToBookingDto(bookingRepository.findPastBookingItemByOwner(userId, BookingState.APPROVED.getCode(), LocalDateTime.now()), userId);
        if (state.equals(BookingState.FUTURE.getCode()))
            return listToBookingDto(bookingRepository.findFutureBookingItemByOwner(userId, LocalDateTime.now()), userId);
        if (state.equals(BookingState.WAITING.getCode()) || state.equals(BookingState.REJECTED.getCode())) {
            return listToBookingDto(bookingRepository.findBookingByOwnerAndStatus(userId, BookingState.valueOf(state)), userId);
        }
        throw new UnsupportedStatus(state);
    }

    private List<BookingDto> listToBookingDto(List<Booking> bookings, long userId) {
        List<BookingDto> bookingDtoList = new ArrayList<>();
        for (Booking booking : bookings)
            bookingDtoList.add(BookingMapper.toBookingDto(booking, itemService.getItemById(booking.getItem().getId(), userId), userService.getUserById(booking.getBooker().getId())));
        return bookingDtoList;
    }

    private boolean checkBooking(LocalDateTime start, long itemId) {
        for (Booking booking : bookingRepository.findByItemIdAndStatus(itemId, BookingState.APPROVED)) {
            if (start.isAfter(booking.getStart()) & start.isBefore(booking.getEnd()) & booking.getStatus().equals(BookingState.APPROVED))
                return true;
        }
        return false;
    }
}
