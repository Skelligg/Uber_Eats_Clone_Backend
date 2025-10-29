package be.kdg.prog6.ordering.port.out.dishProjection;

import be.kdg.prog6.ordering.domain.projection.DishProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadDishesPort {
    Optional<DishProjection> loadDish(UUID dishId);
    List<DishProjection> loadAllByRestaurantId(UUID restaurantId);
}
