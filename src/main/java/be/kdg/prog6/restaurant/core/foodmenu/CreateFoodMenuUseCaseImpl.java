package be.kdg.prog6.restaurant.core.foodmenu;

import be.kdg.prog6.common.events.foodmenu.FoodMenuCreatedEvent;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.port.in.foodmenu.CreateFoodMenuUseCase;
import be.kdg.prog6.restaurant.port.out.foodmenu.PublishFoodMenuEventPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.UpdateFoodMenuPort;
import org.springframework.stereotype.Service;

@Service
public class CreateFoodMenuUseCaseImpl implements CreateFoodMenuUseCase {
    private final UpdateFoodMenuPort updateFoodMenuPort;
    private final PublishFoodMenuEventPort publishFoodMenuEventPort;

    public CreateFoodMenuUseCaseImpl(UpdateFoodMenuPort updateFoodMenuPort, PublishFoodMenuEventPort publishFoodMenuEventPort) {
        this.updateFoodMenuPort = updateFoodMenuPort;
        this.publishFoodMenuEventPort = publishFoodMenuEventPort;
    }

    @Override
    public void createMenuForRestaurant(RestaurantId restaurantId) {
        FoodMenu foodMenu = new FoodMenu(restaurantId);

        foodMenu.addDomainEvent(new FoodMenuCreatedEvent(foodMenu.getRestaurantId().id()));
        this.updateFoodMenuPort.addFoodMenu(foodMenu);
        this.publishFoodMenuEventPort.publishFoodMenuCreated(foodMenu);
    }
}
