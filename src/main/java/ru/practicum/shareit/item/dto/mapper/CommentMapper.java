package ru.practicum.shareit.item.dto.mapper;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .setId(comment.getId())
                .setText(comment.getText())
                .setAuthorName(comment.getAuthor().getName())
                .setCreated(comment.getCreated()).build();
    }

    public static Comment toComment(CommentDto commentDto, User user, Item item) {
        return Comment.builder()
                .setId(commentDto.getId())
                .setItem(item)
                .setText(commentDto.getText())
                .setAuthor(user)
                .setCreated(LocalDateTime.now()).build();
    }
}
