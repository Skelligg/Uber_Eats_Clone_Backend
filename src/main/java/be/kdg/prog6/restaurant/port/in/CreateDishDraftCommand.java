package be.kdg.prog6.restaurant.port.in;

import be.kdg.prog6.restaurant.domain.vo.dish.DISH_TYPE;
import be.kdg.prog6.restaurant.domain.vo.Price;

import java.util.UUID;

public record CreateDishDraftCommand(
        UUID foodMenuId,
        String name,
        String description,
        Price price,
        String pictureUrl,
        String tags,
        DISH_TYPE dishType
) {}
