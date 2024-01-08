package ru.practicum.shareit.item.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotEnoughPermissions;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Autowired
    public ItemServiceImpl(@Qualifier("itemInMemoryStorage") ItemStorage itemStorage,
                           @Qualifier("userInMemoryStorage") UserStorage userStorage) {
        this.itemStorage = itemStorage;
        this.userStorage = userStorage;
    }

    @Override
    public Item addItem(ItemDto itemDto, long userId) {
        if (userStorage.getUserById(userId) == null)
            throw new NotFoundException("Request header without  X-Sharer-User-Id or user not found by userId");
        return itemStorage.addItem(ItemMapper.toItem(null, itemDto, userStorage.getUserById(userId), null));
    }

    @Override
    public Item updateItem(ItemDto itemDto, long itemId, long userId) {
        if (userStorage.getUserById(userId) == null)
            throw new NotFoundException("Request header without  X-Sharer-User-Id or user not found by userId");
        if (itemStorage.getItemById(itemId).getOwner().getId() != userId)
            throw new NotEnoughPermissions("User " + userId + " is not owner item " + itemId);
        return itemStorage.updateItem(ItemMapper.toItem(itemId, itemDto, userStorage.getUserById(userId), null));
    }

    @Override
    public Item getItemById(long itemId) {
        return itemStorage.getItemById(itemId);
    }

    @Override
    public List<Item> getItems(long userId) {
        return itemStorage.getItemByUser(userId);
    }

    @Override
    public List<Item> getItems(String searchText) {
        if (searchText.isEmpty())
            return new ArrayList<>();
        return itemStorage.getItemByText(searchText);
    }
}
