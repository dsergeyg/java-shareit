package ru.practicum.shareit.request.dto.mapper;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;


public class ItemRequestMapper {

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        return new ItemRequestDto(itemRequest.getId(),
                itemRequest.getDescription(),
                itemRequest.getRequestor().getId(),
                itemRequest.getCreated());
    }

    public static ItemRequest toItemRequest(ItemRequestDto itemRequestDto, User requestor) {
        return new ItemRequest(itemRequestDto.getId(),
                itemRequestDto.getDescription(),
                requestor,
                itemRequestDto.getCreated());
    }
}
