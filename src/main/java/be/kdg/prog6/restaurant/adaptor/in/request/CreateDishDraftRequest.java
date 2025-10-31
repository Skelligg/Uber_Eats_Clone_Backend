package be.kdg.prog6.restaurant.adaptor.in.request;

import be.kdg.prog6.common.vo.DISH_TYPE;

public record CreateDishDraftRequest(
        String restaurantId,
        String name,
        String description,
        double price,
        String pictureUrl,
        String tags,
        String dishType
) {

}
