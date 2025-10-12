package be.kdg.prog6.restaurant.port.out;

import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;

import java.util.UUID;

public interface UpdateFoodMenuPort {
    void addFoodMenu(FoodMenu menu);
    void updateFoodMenu(FoodMenu menu);
    void addDishToMenu(Dish dish, FoodMenu menu);
}