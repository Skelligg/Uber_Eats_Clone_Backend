package be.kdg.prog6.restaurant.port.in.foodmenu;

import be.kdg.prog6.restaurant.domain.FoodMenu;

import java.util.UUID;

public interface GetFoodMenuUseCase {
    public FoodMenu getFoodMenu(UUID restaurantId);
}
