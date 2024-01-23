package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {

    Item addItem(Item item);

    Item updateItem(long itemId, ItemDto itemDto);

    Item getItemById(Long id);

    List<Item> getItemByUser(Long userId);

    List<Item> getItemByText(String text);
}
