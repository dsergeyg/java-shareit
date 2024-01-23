package ru.practicum.shareit.user.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.user.model.User;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u.email FROM users AS u", nativeQuery = true)
    Set<String> findAllEmails();
}
