package ru.practicum.shareit.item.dto.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder().setName(item.getName())
                .setDescription(item.getDescription())
                .setAvailable(item.getAvailable()).build();
    }


    public static Item toItem(Long itemId, ItemDto itemDto, User user, ItemRequest itemRequest) {
        return Item.builder().setId(itemId)
                .setOwner(user)
                .setName(itemDto.getName())
                .setDescription(itemDto.getDescription())
                .setAvailable(itemDto.getAvailable())
                .setRequest(itemRequest).build();
    }
}
