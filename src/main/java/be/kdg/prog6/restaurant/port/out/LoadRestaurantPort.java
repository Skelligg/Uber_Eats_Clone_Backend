package be.kdg.prog6.restaurant.port.out;

import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.restaurant.OwnerId;

import java.util.Optional;

public interface LoadRestaurantPort {
    Optional<Restaurant> findByOwnerId(OwnerId ownerId);
}
