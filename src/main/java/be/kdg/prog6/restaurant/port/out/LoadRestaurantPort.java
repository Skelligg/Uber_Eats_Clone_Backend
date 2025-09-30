package be.kdg.prog6.restaurant.port.out;

import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.OwnerId;
import be.kdg.prog6.restaurant.domain.vo.RestaurantId;

import java.util.Optional;

public interface LoadRestaurantPort {
    Optional<Restaurant> LoadBy(OwnerId ownerId);
}
