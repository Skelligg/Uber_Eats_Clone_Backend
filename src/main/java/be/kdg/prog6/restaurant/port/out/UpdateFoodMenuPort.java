package be.kdg.prog6.restaurant.port.out;

import be.kdg.prog6.restaurant.domain.FoodMenu;

public interface UpdateFoodMenuPort {
    void addFoodMenu(FoodMenu menu);
}