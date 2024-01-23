package ru.practicum.shareit.item.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemService {
    @Transactional
    ItemDto addItem(ItemDto itemDto, long userId);

    @Transactional
    ItemDto updateItem(ItemDto itemDto, long itemId, long userId);

    @Transactional(readOnly = true)
    ItemDto getItemById(long itemId, long userId);

    @Transactional(readOnly = true)
    Item getFullItemById(long itemId);

    @Transactional(readOnly = true)
    List<ItemDto> getItems(long userId);

    @Transactional(readOnly = true)
    List<ItemDto> getItems(String searchText);

    @Transactional(readOnly = true)
    List<Comment> getCommentsToItem(long itemId);

    @Transactional
    CommentDto addCommentToItem(CommentDto commentDto, long itemId, long userId);
}
