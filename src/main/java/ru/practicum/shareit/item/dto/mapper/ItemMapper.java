package ru.practicum.shareit.item.dto.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        return new ItemDto(item.getName(),
                item.getDescription(),
                item.getAvailable());
    }


    public static Item toItem(Long itemId, ItemDto itemDto, User user, ItemRequest itemRequest) {
        return new Item(itemId,
                user,
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                itemRequest);
    }
}
