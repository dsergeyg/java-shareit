package ru.practicum.shareit.item.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exception.NotEnoughData;
import ru.practicum.shareit.exception.NotEnoughPermissions;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoBookingInfo;
import ru.practicum.shareit.item.dto.mapper.CommentMapper;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.storage.CommentRepository;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public ItemDto addItem(ItemDto itemDto, long userId) {
        if (userRepository.findById(userId).isEmpty())
            throw new NotFoundException("Request header without  X-Sharer-User-Id or user not found by userId");
        return ItemMapper.toItemDto(itemRepository.save(ItemMapper.toItem(itemDto, userRepository.findById(userId).get(), null)));
    }

    @Transactional
    @Override
    public ItemDto updateItem(ItemDto itemDto, long itemId, long userId) {
        Item curItem = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));
        Booking lastBooking = bookingRepository.findFirstByItemIdAndStartIsBeforeAndStatus(curItem.getId(), LocalDateTime.now(), BookingState.APPROVED, Sort.by("start").descending());
        Booking nextBooking = bookingRepository.findFirstByItemIdAndStartIsAfterAndStatus(curItem.getId(), LocalDateTime.now(), BookingState.APPROVED, Sort.by("start").ascending());
        
        List<Comment> comments = getCommentsToItem(curItem.getId());
        ItemDtoBookingInfo lastItemBooking = null;
        if (lastBooking != null)
            lastItemBooking = ItemDtoBookingInfo.builder().setId(lastBooking.getId()).setBookerId(lastBooking.getBooker().getId()).build();
        ItemDtoBookingInfo nextItemBooking = null;
        if (nextBooking != null)
            nextItemBooking = ItemDtoBookingInfo.builder().setId(nextBooking.getId()).setBookerId(nextBooking.getBooker().getId()).build();

        if (userRepository.findById(userId).isEmpty())
            throw new NotFoundException("Request header without X-Sharer-User-Id or user not found by userId");
        if (itemRepository.findById(itemId).isPresent() && itemRepository.findById(itemId).get().getOwner().getId() != userId)
            throw new NotEnoughPermissions("User " + userId + " is not owner item " + itemId);
        if (itemDto.getName() != null)
            curItem.setName(itemDto.getName());
        if (itemDto.getDescription() != null)
            curItem.setDescription(itemDto.getDescription());
        if (itemDto.getAvailable() != null)
            curItem.setAvailable(itemDto.getAvailable());

        return ItemMapper.toItemDto(itemRepository.save(curItem),
                lastItemBooking,
                nextItemBooking,
                commentDtoList(comments));
    }

    @Transactional(readOnly = true)
    @Override
    public ItemDto getItemById(long itemId, long userId) {
        Item curItem = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));
        List<Comment> comments = getCommentsToItem(itemId);
        if (userId == curItem.getOwner().getId()) {
            Booking lastBooking = bookingRepository.findFirstByItemIdAndStartIsBeforeAndStatus(itemId, LocalDateTime.now(), BookingState.APPROVED, Sort.by("start").descending());
            Booking nextBooking = bookingRepository.findFirstByItemIdAndStartIsAfterAndStatus(itemId, LocalDateTime.now(), BookingState.APPROVED, Sort.by("start").ascending());

            ItemDtoBookingInfo lastItemBooking = null;
            if (lastBooking != null) {
                lastItemBooking = ItemDtoBookingInfo.builder().setId(lastBooking.getId()).setBookerId(lastBooking.getBooker().getId()).build();
            }
            ItemDtoBookingInfo nextItemBooking = null;
            if (nextBooking != null)
                nextItemBooking = ItemDtoBookingInfo.builder().setId(nextBooking.getId()).setBookerId(nextBooking.getBooker().getId()).build();

            return ItemMapper.toItemDto(curItem, lastItemBooking, nextItemBooking, commentDtoList(comments));
        } else
            return ItemMapper.toItemDto(curItem, commentDtoList(comments));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getCommentsToItem(long itemId) {
        List<Comment> comments = commentRepository.findByItemId(itemId, Sort.by("created"));
        if (comments != null)
            return commentRepository.findByItemId(itemId, Sort.by("created"));
        else
            return new ArrayList<>();
    }

    @Transactional
    @Override
    public CommentDto addCommentToItem(CommentDto commentDto, long itemId, long userId) {
        Item curItem = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));
        User curUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Item not found"));
        if (!bookingRepository.findByBookerIdAndEndIsBeforeAndStatus(userId, LocalDateTime.now(), BookingState.APPROVED, Sort.by("start").descending()).isEmpty()) {
            return CommentMapper.toCommentDto(commentRepository.save(new Comment(null, commentDto.getText(), curItem, curUser, LocalDateTime.now())));
        } else
            throw new NotEnoughData("Ended booking for item not found");
    }

    @Transactional(readOnly = true)
    public Item getFullItemById(long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> getItems(long userId) {
        return listToItemDto(itemRepository.findByUserId(userId));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> getItems(String searchText) {
        if (searchText.isEmpty())
            return new ArrayList<>();
        return listToItemDto(itemRepository.findByNameOrDescriptionContaining(searchText, true));
    }

    @Transactional(readOnly = true)
    public List<ItemDto> listToItemDto(List<Item> items) {
        List<ItemDto> curItemDtoList = new ArrayList<>();
        for (Item curItem : items) {
            Booking lastBooking = bookingRepository.findFirstByItemIdAndStartIsBeforeAndStatus(curItem.getId(), LocalDateTime.now(), BookingState.APPROVED, Sort.by("start").descending());
            Booking nextBooking = bookingRepository.findFirstByItemIdAndStartIsAfterAndStatus(curItem.getId(), LocalDateTime.now(), BookingState.APPROVED, Sort.by("start").ascending());
            List<Comment> comments = getCommentsToItem(curItem.getId());

            ItemDtoBookingInfo lastItemBooking = null;
            if (lastBooking != null)
                lastItemBooking = ItemDtoBookingInfo.builder().setId(lastBooking.getId()).setBookerId(lastBooking.getBooker().getId()).build();
            ItemDtoBookingInfo nextItemBooking = null;
            if (nextBooking != null)
                nextItemBooking = ItemDtoBookingInfo.builder().setId(nextBooking.getId()).setBookerId(nextBooking.getBooker().getId()).build();

            curItemDtoList.add(ItemMapper.toItemDto(curItem, lastItemBooking, nextItemBooking, commentDtoList(comments)));
        }
        return curItemDtoList;
    }

    private List<CommentDto> commentDtoList(List<Comment> comments) {
        List<CommentDto> curCommentDtoList = new ArrayList<>();
        for (Comment comment : comments) {
            curCommentDtoList.add(CommentMapper.toCommentDto(comment));
        }
        return curCommentDtoList;
    }
}
