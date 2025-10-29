package be.kdg.prog6.restaurant.core.dish;

import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.port.in.dish.ApplyPendingChangesUseCase;
import be.kdg.prog6.restaurant.port.out.foodmenu.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.dish.UpdateDishPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.UpdateFoodMenuPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyPendingChangesUseCaseImpl implements ApplyPendingChangesUseCase {
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final List<UpdateFoodMenuPort> updateFoodMenuPorts;
    private final UpdateDishPort updateDishPort;

    public ApplyPendingChangesUseCaseImpl(LoadFoodMenuPort loadFoodMenuPort, List<UpdateFoodMenuPort> updateFoodMenuPorts, UpdateDishPort updateDishPort) {
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPorts = updateFoodMenuPorts;
        this.updateDishPort = updateDishPort;
    }

    @Override
    public FoodMenu applyPendingChanges(RestaurantId restaurantId) {
        FoodMenu foodMenu = loadFoodMenuPort.loadBy(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("FoodMenu not found for restaurant: " + restaurantId));

        List<Dish> appliedDishes = foodMenu.applyPendingDrafts();

        this.updateFoodMenuPorts.forEach(updateFoodMenuPort -> updateFoodMenuPort.updateFoodMenu(foodMenu));
        for(Dish dish : appliedDishes) {
            updateDishPort.updateDish(dish);
        }

        return foodMenu;
    }
}
