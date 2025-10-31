package be.kdg.prog6.restaurant.core.dish;

import be.kdg.prog6.common.events.dish.DishPublishedToMenuEvent;
import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.port.in.dish.DishStateChangeCommand;
import be.kdg.prog6.restaurant.port.in.dish.PublishDishUseCase;
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
public class PublishDishUseCaseImpl implements PublishDishUseCase {

    private static final Logger logger = LoggerFactory.getLogger(PublishDishUseCaseImpl.class);

    private final LoadDishPort loadDishPort;
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final List<UpdateFoodMenuPort> updateFoodMenuPorts;
    private final UpdateDishPort updateDishPort;

    public PublishDishUseCaseImpl(LoadDishPort loadDishPort, LoadFoodMenuPort loadFoodMenuPort, List<UpdateFoodMenuPort> updateFoodMenuPorts, UpdateDishPort updateDishPort) {
        this.loadDishPort = loadDishPort;
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPorts = updateFoodMenuPorts;
        this.updateDishPort = updateDishPort;
    }

    @Override
    @Transactional
    public Dish publishDish(DishStateChangeCommand command) {
        Dish dish = loadDishPort.loadDish(command.dishId())
                .orElseThrow(() -> new IllegalArgumentException("Dish not found with id: " + command.dishId()));

        FoodMenu foodMenu = loadFoodMenuPort.loadBy(command.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("FoodMenu not found for restaurant: " + command.restaurantId()));

        try {
            foodMenu.publishDish(dish);

            foodMenu.updateDish(dish);

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

            this.updateFoodMenuPorts.forEach(port -> port.updateFoodMenu(foodMenu));
            updateDishPort.updateDish(dish);

            logger.info("Successfully published dish: {} for restaurant: {}",
                    command.dishId(), command.restaurantId());

            return dish;
        } catch (IllegalStateException e) {
            logger.error("Failed to publish dish: {}", e.getMessage());
            throw e;
        }
    }
}
