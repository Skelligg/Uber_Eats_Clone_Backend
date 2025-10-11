package be.kdg.prog6.restaurant.port.in;

import be.kdg.prog6.restaurant.domain.vo.dish.DISH_TYPE;
import be.kdg.prog6.restaurant.domain.vo.Price;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;

public record CreateDishDraftCommand(
        RestaurantId restaurantId,
        String name,
        String description,
        Price price,
        String pictureUrl,
        String tags,
        DISH_TYPE dishType
) {}
