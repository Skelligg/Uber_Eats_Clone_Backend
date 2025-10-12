package be.kdg.prog6.restaurant.port.in;

import be.kdg.prog6.restaurant.domain.vo.dish.DishId;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;

public record PublishingDishCommand(
        RestaurantId restaurantId,
        DishId dishId
) {
}
