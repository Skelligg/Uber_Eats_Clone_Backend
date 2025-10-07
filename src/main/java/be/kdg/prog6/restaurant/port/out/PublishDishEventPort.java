package be.kdg.prog6.restaurant.port.out;

import be.kdg.prog6.restaurant.domain.Dish;

public interface PublishDishEventPort {
    void publishDishCreated(Dish dish);
}
