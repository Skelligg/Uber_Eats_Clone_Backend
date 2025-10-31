package be.kdg.prog6.restaurant.port.out.restaurant;

import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;

import java.util.Optional;
import java.util.UUID;

public interface LoadRestaurantPort {
    Optional<Restaurant> findByOwnerId(UUID id);
    Optional<Restaurant> findById(RestaurantId restaurantId);
}
