package be.kdg.prog6.restaurant.adaptor.in.response;

import be.kdg.prog6.restaurant.domain.vo.Price;
import be.kdg.prog6.restaurant.domain.vo.dish.DISH_TYPE;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;

public record DishDto (
        String name,
        String description,
        double price,
        String pictureUrl,
        String tags,
        String dishType
) {
}
