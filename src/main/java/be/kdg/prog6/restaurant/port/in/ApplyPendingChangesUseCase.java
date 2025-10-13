package be.kdg.prog6.restaurant.port.in;

import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;

public interface ApplyPendingChangesUseCase {
    FoodMenu applyPendingChanges(RestaurantId restaurantId);
}
