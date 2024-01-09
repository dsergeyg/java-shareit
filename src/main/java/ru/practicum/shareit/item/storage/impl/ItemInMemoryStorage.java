package ru.practicum.shareit.item.storage.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
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

    private final Map<Long, Item> items = new HashMap<>();
    private long idSequence;

    @Override
    public Item addItem(Item item) {
        item.setId(++idSequence);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item updateItem(long itemId, ItemDto itemDto) {
        Item curItem = items.get(itemId);
        if (itemDto.getName() != null)
            curItem.setName(itemDto.getName());
        if (itemDto.getDescription() != null)
            curItem.setDescription(itemDto.getDescription());
        if (itemDto.getAvailable() != null)
            curItem.setAvailable(itemDto.getAvailable());
        return items.get(itemId);
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
        return items.values().stream().filter(o -> o.getAvailable() && (o.getName().toLowerCase().contains(text.toLowerCase()) || o.getDescription().toLowerCase().contains(text.toLowerCase()))).collect(Collectors.toList());
    }
}
