package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item addItem(ItemDto itemDto, long userId);

    Item updateItem(ItemDto itemDto, long itemId, long userId);

    Item getItemById(long itemId);

    List<Item> getItems(long userId);

    List<Item> getItems(String searchText);
}
