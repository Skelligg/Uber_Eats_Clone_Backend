package be.kdg.prog6.restaurant.port.out.restaurant;

import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.restaurant.OwnerId;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;

import java.util.Optional;

public interface LoadRestaurantPort {
    Optional<Restaurant> findByOwnerId(OwnerId ownerId);
    Optional<Restaurant> findById(RestaurantId restaurantId);
}
