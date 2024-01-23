package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class ItemDto {

    long id;
    @NotBlank(message = "Name may not be empty or blank")
    String name;
    @NotBlank(message = "Description may not be empty or blank")
    String description;
    @NotNull(message = "Available may not be empty")
    Boolean available;
    ItemDtoBookingInfo lastBooking;
    ItemDtoBookingInfo nextBooking;
    List<CommentDto> comments;
}
