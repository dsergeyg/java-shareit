package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "set")
public class CommentDto {

    long id;
    @NotBlank
    String text;
    String authorName;
    LocalDateTime created;
}
