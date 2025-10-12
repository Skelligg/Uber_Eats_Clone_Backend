package be.kdg.prog6.restaurant.adaptor.in.request;

import be.kdg.prog6.restaurant.domain.vo.dish.DISH_TYPE;

public record EditDishRequest(
        String name,
        String description,
        double price,
        String pictureUrl,
        String tags,
        DISH_TYPE dishType
) {
}
