package ru.practicum.shareit.item.dto.mapper;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoBookingInfo;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {

    public static ItemDto toItemDto(Item item, ItemDtoBookingInfo lastBooking, ItemDtoBookingInfo nextBooking, List<CommentDto> comments) {
        return ItemDto.builder().setId(item.getId())
                .setName(item.getName())
                .setDescription(item.getDescription())
                .setLastBooking(lastBooking)
                .setNextBooking(nextBooking)
                .setComments(comments)
                .setAvailable(item.getAvailable()).build();
    }

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder().setId(item.getId())
                .setName(item.getName())
                .setDescription(item.getDescription())
                .setAvailable(item.getAvailable())
                .setComments(new ArrayList<>()).build();
    }

    public static ItemDto toItemDto(Item item, List<CommentDto> comments) {
        return ItemDto.builder().setId(item.getId())
                .setName(item.getName())
                .setDescription(item.getDescription())
                .setAvailable(item.getAvailable())
                .setComments(comments).build();
    }

    public static Item toItem(ItemDto itemDto, User user, ItemRequest itemRequest) {
        return Item.builder().setId(itemDto.getId())
                .setOwner(user)
                .setName(itemDto.getName())
                .setDescription(itemDto.getDescription())
                .setAvailable(itemDto.getAvailable())
                .setRequest(itemRequest).build();
    }


}
