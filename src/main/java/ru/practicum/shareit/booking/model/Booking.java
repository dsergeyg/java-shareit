package ru.practicum.shareit.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@AllArgsConstructor
public class Booking implements Update {

    @NotNull(groups = Update.class)
    Long id;
    User booker;
    Item item;
    LocalDateTime start;
    LocalDateTime end;
    BookingState status;

}
