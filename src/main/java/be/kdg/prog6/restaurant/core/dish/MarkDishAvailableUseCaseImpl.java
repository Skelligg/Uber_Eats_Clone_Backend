package be.kdg.prog6.restaurant.core.dish;

import be.kdg.prog6.common.events.dish.DishMarkedAvailableEvent;
import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.port.in.dish.DishStateChangeCommand;
import be.kdg.prog6.restaurant.port.in.dish.MarkDishAvailableUseCase;
import be.kdg.prog6.restaurant.port.out.dish.LoadDishPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.dish.PublishDishEventPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.UpdateFoodMenuPort;
import org.springframework.stereotype.Service;

@Service
public class MarkDishAvailableUseCaseImpl implements MarkDishAvailableUseCase {

    private final LoadDishPort loadDishPort;
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final UpdateFoodMenuPort updateFoodMenuPort;
    private final PublishDishEventPort publishDishEventPort;

    public MarkDishAvailableUseCaseImpl(LoadDishPort loadDishPort, LoadFoodMenuPort loadFoodMenuPort, UpdateFoodMenuPort updateFoodMenuPort, PublishDishEventPort publishDishEventPort) {
        this.loadDishPort = loadDishPort;
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPort = updateFoodMenuPort;
        this.publishDishEventPort = publishDishEventPort;
    }

    @Override
    public Dish markDishAvailable(DishStateChangeCommand command) {
        Dish dish = loadDishPort.loadDish(command.dishId())
                .orElseThrow(() -> new IllegalArgumentException("Dish not found with id: " + command.dishId()));

        FoodMenu foodMenu = loadFoodMenuPort.loadBy(command.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("FoodMenu not found for restaurant: " + command.restaurantId()));

        dish.markAvailable();

        foodMenu.updateDish(dish);

        dish.addDomainEvent(new DishMarkedAvailableEvent(
                dish.getDishId().id()
        ));

        updateFoodMenuPort.updateFoodMenu(foodMenu);
        publishDishEventPort.updateDish(dish);

        return dish;
    }
}
