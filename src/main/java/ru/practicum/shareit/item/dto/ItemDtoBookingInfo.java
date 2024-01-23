package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "set")
public class ItemDtoBookingInfo {
    long id;
    long bookerId;

}
