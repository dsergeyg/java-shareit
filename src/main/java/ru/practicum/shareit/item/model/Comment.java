package ru.practicum.shareit.item.model;

import lombok.*;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments", schema = "public")
@Data
@Builder(setterPrefix = "set")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "description")
    String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    User author;
    @Column(name = "created")
    LocalDateTime created = LocalDateTime.now();
}
