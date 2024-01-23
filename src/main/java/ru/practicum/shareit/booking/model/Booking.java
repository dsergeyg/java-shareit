package ru.practicum.shareit.booking.model;

import lombok.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @ToString.Exclude
    User booker;
    @ManyToOne
    @ToString.Exclude
    Item item;
    @Column(name = "start_date")
    LocalDateTime start;
    @Column(name = "end_date")
    LocalDateTime end;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    BookingState status;

}
