package be.kdg.prog6.restaurant.core;

import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.port.in.ScheduleChangesCommand;
import be.kdg.prog6.restaurant.port.in.SchedulePendingChangesUseCase;
import be.kdg.prog6.restaurant.port.out.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.PublishDishEventPort;
import be.kdg.prog6.restaurant.port.out.UpdateFoodMenuPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulePendingChangesUseCaseImpl implements SchedulePendingChangesUseCase {
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final UpdateFoodMenuPort updateFoodMenuPort;
    private final PublishDishEventPort publishDishEventPort;

    public SchedulePendingChangesUseCaseImpl(LoadFoodMenuPort loadFoodMenuPort, UpdateFoodMenuPort updateFoodMenuPort, PublishDishEventPort publishDishEventPort) {
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPort = updateFoodMenuPort;
        this.publishDishEventPort = publishDishEventPort;
    }

    @Override
    public FoodMenu scheduleChanges(ScheduleChangesCommand command) {
        FoodMenu foodMenu = loadFoodMenuPort.loadBy(command.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("FoodMenu not found for restaurant: " + command.restaurantId()));

        foodMenu.scheduleDishes(command.dishIds(),command.publicationTime(),command.stateToBecome());

        updateFoodMenuPort.updateFoodMenu(foodMenu);

        return foodMenu;
    }

}
