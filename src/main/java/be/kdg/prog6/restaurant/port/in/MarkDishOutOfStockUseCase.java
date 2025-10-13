package be.kdg.prog6.restaurant.port.in;

import be.kdg.prog6.restaurant.domain.Dish;

public interface MarkDishOutOfStockUseCase {
    Dish markDishOutOfStock(DishStateChangeCommand command);
}
