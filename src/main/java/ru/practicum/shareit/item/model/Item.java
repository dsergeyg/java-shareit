package ru.practicum.shareit.item.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

@Entity
@Table(name = "items", schema = "public")
@Data
@Builder(setterPrefix = "set")
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User owner;
    @Column(name = "name")
    private String name;
    @Column(name = "description", length = 512)
    private String description;
    @Column(name = "is_available")
    @ColumnDefault("true")
    private Boolean available;
    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private ItemRequest request;
}
