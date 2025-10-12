package be.kdg.prog6.restaurant.port.out;

import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.restaurant.OwnerId;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;

import java.util.Optional;

public interface LoadFoodMenuPort {
    Optional<FoodMenu> loadBy(RestaurantId restaurantId);

}
