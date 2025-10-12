package be.kdg.prog6.restaurant.port.out;

import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.vo.dish.DishId;

import java.util.Optional;
import java.util.UUID;

public interface LoadDishPort {
    Optional<Dish> loadDish(DishId dishId);
}
