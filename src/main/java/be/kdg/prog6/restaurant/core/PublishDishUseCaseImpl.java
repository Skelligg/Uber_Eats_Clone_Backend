package be.kdg.prog6.restaurant.core;

import be.kdg.prog6.common.events.DishPublishedToMenuEvent;
import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.port.in.DishStateChangeCommand;
import be.kdg.prog6.restaurant.port.in.PublishDishUseCase;
import be.kdg.prog6.restaurant.port.out.LoadDishPort;
import be.kdg.prog6.restaurant.port.out.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.PublishDishEventPort;
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
    private final PublishDishEventPort publishDishEventPort;

    public PublishDishUseCaseImpl(LoadDishPort loadDishPort, LoadFoodMenuPort loadFoodMenuPort, UpdateFoodMenuPort updateFoodMenuPort, PublishDishEventPort publishDishEventPort) {
        this.loadDishPort = loadDishPort;
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPort = updateFoodMenuPort;
        this.publishDishEventPort = publishDishEventPort;
    }

    @Override
    @Transactional
    public Dish publishDish(DishStateChangeCommand command) {
        Dish dish = loadDishPort.loadDish(command.dishId())
                .orElseThrow(() -> new IllegalArgumentException("Dish not found with id: " + command.dishId()));

        FoodMenu foodMenu = loadFoodMenuPort.loadBy(command.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("FoodMenu not found for restaurant: " + command.restaurantId()));

        try {
            dish.publish();

            foodMenu.addDish(dish);

            dish.addDomainEvent(new DishPublishedToMenuEvent(
                    dish.getDishId().id(),
                    foodMenu.getRestaurantId().id(),
                    dish.getPublishedVersion().orElseThrow().name(),
                    dish.getPublishedVersion().orElseThrow().description(),
                    dish.getPublishedVersion().orElseThrow().price().amount(),
                    dish.getPublishedVersion().orElseThrow().pictureUrl(),
                    dish.getPublishedVersion().orElseThrow().tags(),
                    dish.getPublishedVersion().orElseThrow().dishType().toString(),
                    dish.getState().toString()
                    )
            );

            // Persist the changes
            updateFoodMenuPort.updateFoodMenu(foodMenu);
            publishDishEventPort.updateDish(dish);

            logger.info("Successfully published dish: {} for restaurant: {}",
                    command.dishId(), command.restaurantId());

            return dish;
        } catch (IllegalStateException e) {
            logger.error("Failed to publish dish: {}", e.getMessage());
            throw e;
        }
    }
}
