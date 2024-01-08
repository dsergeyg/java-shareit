package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@AllArgsConstructor
@Data
public class ItemDto {

    @NotBlank(message = "Name may not be empty or blank")
    String name;
    @NotBlank(message = "Description may not be empty or blank")
    String description;
    @NotNull(message = "Available may not be empty")
    Boolean available;

}
