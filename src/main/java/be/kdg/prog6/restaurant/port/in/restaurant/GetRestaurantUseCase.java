package be.kdg.prog6.restaurant.port.in.restaurant;

import be.kdg.prog6.restaurant.domain.Restaurant;

import java.util.UUID;

public interface GetRestaurantUseCase {
    Restaurant getRestaurant(UUID ownerId);
}
