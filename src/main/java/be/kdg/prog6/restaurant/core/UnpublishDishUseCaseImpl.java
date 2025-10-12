package be.kdg.prog6.restaurant.core;

import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.port.in.PublishingDishCommand;
import be.kdg.prog6.restaurant.port.in.UnpublishDishUseCase;
import be.kdg.prog6.restaurant.port.out.LoadDishPort;
import be.kdg.prog6.restaurant.port.out.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.UpdateFoodMenuPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnpublishDishUseCaseImpl implements UnpublishDishUseCase {
    private final Logger logger = LoggerFactory.getLogger(UnpublishDishUseCaseImpl.class);

    private final LoadDishPort loadDishPort;
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final UpdateFoodMenuPort updateFoodMenuPort;

    public UnpublishDishUseCaseImpl(LoadDishPort loadDishPort, LoadFoodMenuPort loadFoodMenuPort, UpdateFoodMenuPort updateFoodMenuPort) {
        this.loadDishPort = loadDishPort;
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPort = updateFoodMenuPort;
    }

    @Override
    @Transactional
    public Dish unpublishDish(PublishingDishCommand command) {
        // Load the dish
        Dish dish = loadDishPort.loadDish(command.dishId())
                .orElseThrow(() -> new IllegalArgumentException("Dish not found with id: " + command.dishId()));

        // Load the food menu to check invariants
        FoodMenu foodMenu = loadFoodMenuPort.LoadBy(command.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("FoodMenu not found for restaurant: " + command.restaurantId()));

        dish.unpublish();

        foodMenu.updateDish(dish);

        updateFoodMenuPort.updateFoodMenu(foodMenu);

        foodMenu.removeDish(dish);


        logger.info("Successfully unpublished dish: {} for restaurant: {}",
                command.dishId(), command.restaurantId());

        return dish;
    }
}
