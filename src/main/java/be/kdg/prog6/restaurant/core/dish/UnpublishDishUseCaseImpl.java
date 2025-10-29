package be.kdg.prog6.restaurant.core.dish;

import be.kdg.prog6.common.events.dish.DishUnpublishedToMenuEvent;
import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.port.in.dish.DishStateChangeCommand;
import be.kdg.prog6.restaurant.port.in.dish.UnpublishDishUseCase;
import be.kdg.prog6.restaurant.port.out.dish.LoadDishPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.dish.UpdateDishPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.UpdateFoodMenuPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnpublishDishUseCaseImpl implements UnpublishDishUseCase {
    private final Logger logger = LoggerFactory.getLogger(UnpublishDishUseCaseImpl.class);

    private final LoadDishPort loadDishPort;
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final List<UpdateFoodMenuPort> updateFoodMenuPorts;
    private final UpdateDishPort updateDishPort;

    public UnpublishDishUseCaseImpl(LoadDishPort loadDishPort, LoadFoodMenuPort loadFoodMenuPort, List<UpdateFoodMenuPort> updateFoodMenuPorts, UpdateDishPort updateDishPort) {
        this.loadDishPort = loadDishPort;
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPorts = updateFoodMenuPorts;
        this.updateDishPort = updateDishPort;
    }

    @Override
    @Transactional
    public Dish unpublishDish(DishStateChangeCommand command) {

        Dish dish = loadDishPort.loadDish(command.dishId())
                .orElseThrow(() -> new IllegalArgumentException("Dish not found with id: " + command.dishId()));

        FoodMenu foodMenu = loadFoodMenuPort.loadBy(command.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("FoodMenu not found for restaurant: " + command.restaurantId()));

        dish.unpublish();

        foodMenu.updateDish(dish);

        dish.addDomainEvent(new DishUnpublishedToMenuEvent(
                dish.getDishId().id()
        ) );

        this.updateFoodMenuPorts.forEach(port -> port.updateFoodMenu(foodMenu));
        updateDishPort.updateDish(dish);

        return dish;
    }
}
