package be.kdg.prog6.restaurant.core.dish;

import be.kdg.prog6.common.events.dish.DishMarkedOutOfStockEvent;
import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.port.in.dish.MarkDishOutOfStockUseCase;
import be.kdg.prog6.restaurant.port.in.dish.DishStateChangeCommand;
import be.kdg.prog6.restaurant.port.out.dish.LoadDishPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.dish.UpdateDishPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.UpdateFoodMenuPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkDishOutOfStockUseCaseImpl implements MarkDishOutOfStockUseCase {

    private final LoadDishPort loadDishPort;
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final List<UpdateFoodMenuPort> updateFoodMenuPorts;
    private final UpdateDishPort updateDishPort;

    public MarkDishOutOfStockUseCaseImpl(LoadDishPort loadDishPort, LoadFoodMenuPort loadFoodMenuPort, List<UpdateFoodMenuPort> updateFoodMenuPorts, UpdateDishPort updateDishPort) {
        this.loadDishPort = loadDishPort;
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPorts = updateFoodMenuPorts;
        this.updateDishPort = updateDishPort;
    }

    @Override
    public Dish markDishOutOfStock(DishStateChangeCommand command) {
        Dish dish = loadDishPort.loadDish(command.dishId())
                .orElseThrow(() -> new IllegalArgumentException("Dish not found with id: " + command.dishId()));

        FoodMenu foodMenu = loadFoodMenuPort.loadBy(command.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("FoodMenu not found for restaurant: " + command.restaurantId()));

        dish.markOutOfStock();

        foodMenu.updateDish(dish);

        dish.addDomainEvent(new DishMarkedOutOfStockEvent(
                dish.getDishId().id()
        ));

        this.updateFoodMenuPorts.forEach(port -> port.updateFoodMenu(foodMenu));
        updateDishPort.updateDish(dish);

        return dish;
    }
}
