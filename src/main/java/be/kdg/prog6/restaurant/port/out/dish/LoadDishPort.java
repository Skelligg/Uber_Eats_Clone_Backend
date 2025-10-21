package be.kdg.prog6.restaurant.port.out.dish;

import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.vo.dish.DishId;

import java.util.Optional;

public interface LoadDishPort {
    Optional<Dish> loadDish(DishId dishId);
}
