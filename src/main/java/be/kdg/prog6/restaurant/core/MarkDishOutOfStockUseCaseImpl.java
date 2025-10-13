package be.kdg.prog6.restaurant.core;

import be.kdg.prog6.common.events.DishMarkedOutOfStockEvent;
import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.port.in.MarkDishOutOfStockUseCase;
import be.kdg.prog6.restaurant.port.in.DishStateChangeCommand;
import be.kdg.prog6.restaurant.port.out.LoadDishPort;
import be.kdg.prog6.restaurant.port.out.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.PublishDishEventPort;
import be.kdg.prog6.restaurant.port.out.UpdateFoodMenuPort;
import org.springframework.stereotype.Service;

@Service
public class MarkDishOutOfStockUseCaseImpl implements MarkDishOutOfStockUseCase {

    private final LoadDishPort loadDishPort;
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final UpdateFoodMenuPort updateFoodMenuPort;
    private final PublishDishEventPort publishDishEventPort;

    public MarkDishOutOfStockUseCaseImpl(LoadDishPort loadDishPort, LoadFoodMenuPort loadFoodMenuPort, UpdateFoodMenuPort updateFoodMenuPort, PublishDishEventPort publishDishEventPort) {
        this.loadDishPort = loadDishPort;
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPort = updateFoodMenuPort;
        this.publishDishEventPort = publishDishEventPort;
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

        updateFoodMenuPort.updateFoodMenu(foodMenu);
        publishDishEventPort.updateDish(dish);

        return dish;
    }
}
