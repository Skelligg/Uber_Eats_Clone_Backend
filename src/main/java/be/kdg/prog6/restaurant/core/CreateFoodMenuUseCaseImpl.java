package be.kdg.prog6.restaurant.core;

import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.port.in.CreateFoodMenuUseCase;
import be.kdg.prog6.restaurant.port.out.UpdateFoodMenuPort;
import org.springframework.stereotype.Service;

@Service
public class CreateFoodMenuUseCaseImpl implements CreateFoodMenuUseCase {
    private final UpdateFoodMenuPort updateFoodMenuPort;

    public CreateFoodMenuUseCaseImpl(UpdateFoodMenuPort updateFoodMenuPort) {
        this.updateFoodMenuPort = updateFoodMenuPort;
    }

    @Override
    public void createMenuForRestaurant(RestaurantId restaurantId) {
        FoodMenu foodMenu = new FoodMenu(restaurantId);
        updateFoodMenuPort.addFoodMenu(foodMenu);
    }
}
