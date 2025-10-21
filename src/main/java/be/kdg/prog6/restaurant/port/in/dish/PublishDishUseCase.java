package be.kdg.prog6.restaurant.port.in.dish;

import be.kdg.prog6.restaurant.domain.Dish;

public interface PublishDishUseCase {
    Dish publishDish(DishStateChangeCommand command);
}
