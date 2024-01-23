package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", nullable = false, length = 512, unique = true)
    @NotNull
    private String email;
    @Column(name = "name")
    @NotNull
    private String name;
}
