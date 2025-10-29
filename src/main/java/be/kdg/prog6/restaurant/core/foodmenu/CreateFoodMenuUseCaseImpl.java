package be.kdg.prog6.restaurant.core.foodmenu;

import be.kdg.prog6.common.events.foodmenu.FoodMenuCreatedEvent;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.port.in.foodmenu.CreateFoodMenuUseCase;
import be.kdg.prog6.restaurant.port.out.foodmenu.UpdateFoodMenuPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CreateFoodMenuUseCaseImpl implements CreateFoodMenuUseCase {
    private final List<UpdateFoodMenuPort> updateFoodMenuPorts;

    public CreateFoodMenuUseCaseImpl(List<UpdateFoodMenuPort> updateFoodMenuPorts) {
        this.updateFoodMenuPorts = updateFoodMenuPorts;
    }

    @Override
    public void createMenuForRestaurant(RestaurantId restaurantId) {
        FoodMenu foodMenu = new FoodMenu(restaurantId);

        foodMenu.addDomainEvent(new FoodMenuCreatedEvent(foodMenu.getRestaurantId().id()));
        this.updateFoodMenuPorts.forEach(port -> port.updateFoodMenu(foodMenu));
    }
}
