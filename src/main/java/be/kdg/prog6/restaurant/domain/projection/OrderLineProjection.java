package be.kdg.prog6.restaurant.domain.projection;

import be.kdg.prog6.restaurant.domain.vo.dish.DishId;

public record OrderLineProjection(
        DishId dishId,
        String dishName,
        int quantity,
        double unitPrice,
        double linePrice
) {
}
