package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    ItemDto addItem(ItemDto itemDto, long userId);

    ItemDto updateItem(ItemDto itemDto, long itemId, long userId);

    ItemDto getItemById(long itemId, long userId);

    Item getFullItemById(long itemId);

    List<ItemDto> getItems(long userId);

    List<ItemDto> getItems(String searchText);

    List<Comment> getCommentsToItem(long itemId);

    CommentDto addCommentToItem(CommentDto commentDto, long itemId, long userId);
}
