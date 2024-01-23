package ru.practicum.shareit.item.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT i.* " +
            "FROM items AS i " +
            "WHERE i.owner_id = ?1", nativeQuery = true)
    List<Item> findByUserId(long userId);

    @Query("SELECT i FROM Item i WHERE (LOWER(i.name) like '%'||LOWER(?1)||'%' OR LOWER(i.description) like '%'||LOWER(?1)||'%') AND i.available = ?2")
    List<Item> findByNameOrDescriptionContaining(String nameText, boolean available);
}
