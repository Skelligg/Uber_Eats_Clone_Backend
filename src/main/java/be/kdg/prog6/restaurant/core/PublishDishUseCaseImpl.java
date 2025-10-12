package be.kdg.prog6.restaurant.core;

import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.port.in.PublishingDishCommand;
import be.kdg.prog6.restaurant.port.in.PublishDishUseCase;
import be.kdg.prog6.restaurant.port.out.LoadDishPort;
import be.kdg.prog6.restaurant.port.out.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.UpdateFoodMenuPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PublishDishUseCaseImpl implements PublishDishUseCase {

    private static final Logger logger = LoggerFactory.getLogger(PublishDishUseCaseImpl.class);

    private final LoadDishPort loadDishPort;
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final UpdateFoodMenuPort updateFoodMenuPort;

    public PublishDishUseCaseImpl(LoadDishPort loadDishPort, LoadFoodMenuPort loadFoodMenuPort,UpdateFoodMenuPort updateFoodMenuPort) {
        this.loadDishPort = loadDishPort;
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPort = updateFoodMenuPort;
    }

    @Override
    @Transactional
    public Dish publishDish(PublishingDishCommand command) {
        // Load the dish
        Dish dish = loadDishPort.loadDish(command.dishId())
                .orElseThrow(() -> new IllegalArgumentException("Dish not found with id: " + command.dishId()));

        // Load the food menu to check invariants
        FoodMenu foodMenu = loadFoodMenuPort.LoadBy(command.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("FoodMenu not found for restaurant: " + command.restaurantId()));

        // Check if we can publish (FoodMenu enforces the 10-dish limit)
        // The foodMenu.updateDish() will trigger recalculateAveragePrice
        try {
            // Publish the dish (domain logic)
            dish.publishNow();

            // Update the dish in the food menu (this checks the invariant)
            foodMenu.addDish(dish);

            // Persist the changes
            updateFoodMenuPort.updateFoodMenu(foodMenu);

            logger.info("Successfully published dish: {} for restaurant: {}",
                    command.dishId(), command.restaurantId());

            return dish;
        } catch (IllegalStateException e) {
            logger.error("Failed to publish dish: {}", e.getMessage());
            throw e;
        }
    }
}
