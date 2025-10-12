package be.kdg.prog6.restaurant.port.in;

import be.kdg.prog6.restaurant.domain.Dish;

public interface EditDishUseCase {
    Dish editDish(EditDishCommand command);
}
