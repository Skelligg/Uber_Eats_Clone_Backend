package be.kdg.prog6.restaurant.port.out.foodmenu;

import be.kdg.prog6.restaurant.domain.FoodMenu;

public interface PublishFoodMenuEventPort {
    void publishFoodMenuCreated(FoodMenu foodMenu);
}
