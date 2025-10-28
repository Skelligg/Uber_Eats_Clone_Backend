package be.kdg.prog6.restaurant.port.in.restaurant;

import be.kdg.prog6.restaurant.domain.Restaurant;

public interface CreateRestaurantUseCase {
    Restaurant createRestaurant(CreateRestaurantCommand command);
}
