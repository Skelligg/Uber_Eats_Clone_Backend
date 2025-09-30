package be.kdg.prog6.restaurant.port.in;

import be.kdg.prog6.restaurant.domain.Restaurant;

public interface CreateRestaurantUseCase {
    Restaurant createRestaurant(CreateRestaurantCommand command);
}
