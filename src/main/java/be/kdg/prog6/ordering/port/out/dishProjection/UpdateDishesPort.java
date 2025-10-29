package be.kdg.prog6.ordering.port.out.dishProjection;

import be.kdg.prog6.ordering.domain.projection.DishProjection;

public interface UpdateDishesPort {
    void updateDishes(DishProjection projection);
    void removeDish(DishProjection projection);
}
