package ru.practicum.shareit.item.dto.mapper;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;

public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .setId(comment.getId())
                .setText(comment.getText())
                .setAuthorName(comment.getAuthor().getName())
                .setCreated(comment.getCreated()).build();
    }
}
