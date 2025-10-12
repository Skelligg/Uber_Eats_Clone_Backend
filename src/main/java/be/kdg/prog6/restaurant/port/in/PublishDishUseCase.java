package be.kdg.prog6.restaurant.port.in;

import be.kdg.prog6.restaurant.domain.Dish;

public interface PublishDishUseCase {
    Dish publishDish(PublishingDishCommand command);
}
