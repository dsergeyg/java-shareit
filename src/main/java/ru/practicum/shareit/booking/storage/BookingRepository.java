package ru.practicum.shareit.booking.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByItemIdAndStatus(long itemId, BookingState status);

    List<Booking> findByBookerId(long userId, Sort sort);

    List<Booking> findByBookerIdAndEndAfterAndStartBefore(long userId, LocalDateTime end, LocalDateTime start, Sort sort);

    List<Booking> findByBookerIdAndEndIsBeforeAndStatus(long userId, LocalDateTime end, BookingState status, Sort sort);

    @Query(value = "SELECT b.* FROM bookings AS b " +
            "WHERE b.booker_id = ?1 AND (LOWER(b.status) like 'approved' OR LOWER(b.status) like 'waiting') AND b.start_date > current_timestamp ORDER BY b.start_date DESC", nativeQuery = true)
    List<Booking> findFutureBookingItemByCurUser(long userId);

    List<Booking> findByBookerIdAndStatus(long userId, BookingState status, Sort sort);

    @Query("SELECT b FROM Booking AS b " +
            "JOIN b.item AS i " +
            "JOIN i.owner AS u " +
            "where u.id = ?1 " +
            "ORDER BY b.start DESC")
    List<Booking> findAllBookingItemByOwner(long userId);

    @Query("SELECT b FROM Booking AS b " +
            "JOIN b.item AS i " +
            "JOIN i.owner AS u " +
            "where u.id = ?1 " +
            "AND b.start < ?2 AND b.end > ?3 " +
            "ORDER BY b.start DESC")
    List<Booking> findCurrentBookingItemByOwner(long userId, LocalDateTime end, LocalDateTime start);

    @Query("SELECT b FROM Booking AS b " +
            "JOIN b.item AS i " +
            "JOIN i.owner AS u " +
            "where u.id = ?1 " +
            "AND LOWER(b.status) like LOWER(?2) AND b.end < ?3 " +
            "ORDER BY b.start DESC")
    List<Booking> findPastBookingItemByOwner(long userId, String state, LocalDateTime end);

    @Query("SELECT b FROM Booking AS b " +
            "JOIN b.item AS i " +
            "JOIN i.owner AS u " +
            "where u.id = ?1 " +
            "AND (LOWER(b.status) like 'approved' OR LOWER(b.status) like 'waiting') " +
            "AND b.start > ?2 " +
            "ORDER BY b.start DESC")
    List<Booking> findFutureBookingItemByOwner(long userId, LocalDateTime start);

    @Query("SELECT b FROM Booking AS b " +
            "JOIN b.item AS i " +
            "JOIN i.owner AS u " +
            "where u.id = ?1 " +
            "AND b.status like ?2 " +
            "ORDER BY b.start DESC")
    List<Booking> findBookingByOwnerAndStatus(long userId, BookingState status);

    Booking findFirstByItemIdAndStartIsBeforeAndStatus(long itemId, LocalDateTime end, BookingState status, Sort sort);

    Booking findFirstByItemIdAndStartIsAfterAndStatus(long itemId, LocalDateTime end, BookingState status, Sort sort);
}
