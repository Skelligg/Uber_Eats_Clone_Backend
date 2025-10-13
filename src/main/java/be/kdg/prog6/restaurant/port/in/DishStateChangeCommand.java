package be.kdg.prog6.restaurant.port.in;

import be.kdg.prog6.restaurant.domain.vo.dish.DishId;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;

public record DishStateChangeCommand(
        RestaurantId restaurantId,
        DishId dishId
) {
}
