package ru.practicum.shareit.item.storage.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@Component("itemInMemoryStorage")
public class ItemInMemoryStorage implements ItemStorage {

    Map<Long, Item> items = new HashMap<>();
    private long idSequence;

    @Override
    public Item addItem(Item item) {
        item.setId(++idSequence);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item updateItem(Item item) {
        if (item.getName() != null)
            items.get(item.getId()).setName(item.getName());
        if (item.getDescription() != null)
            items.get(item.getId()).setDescription(item.getDescription());
        if (item.getAvailable() != null)
            items.get(item.getId()).setAvailable(item.getAvailable());
        return items.get(item.getId());
    }

    @Override
    public Item getItemById(Long id) {
        return items.get(id);
    }

    @Override
    public List<Item> getItemByUser(Long userId) {
        return items.values().stream().filter(o -> Objects.equals(o.getOwner().getId(), userId)).collect(Collectors.toList());
    }

    @Override
    public List<Item> getItemByText(String text) {
        return items.values().stream().filter(o -> o.getAvailable() & (o.getName().toLowerCase().contains(text.toLowerCase()) || o.getDescription().toLowerCase().contains(text.toLowerCase()))).collect(Collectors.toList());
    }
}
