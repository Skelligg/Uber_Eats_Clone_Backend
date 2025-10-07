package be.kdg.prog6.restaurant.port.out;

import be.kdg.prog6.restaurant.domain.Dish;

import java.util.UUID;

public interface UpdateDishPort {
    void addDish(Dish dish, UUID restaurantId);
}
