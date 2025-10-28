package be.kdg.prog6.restaurant.core.dish;

import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.port.in.dish.ScheduleChangesCommand;
import be.kdg.prog6.restaurant.port.in.dish.SchedulePendingChangesUseCase;
import be.kdg.prog6.restaurant.port.out.foodmenu.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.dish.PublishDishEventPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.UpdateFoodMenuPort;
import org.springframework.stereotype.Service;

@Service
public class SchedulePendingChangesUseCaseImpl implements SchedulePendingChangesUseCase {
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final UpdateFoodMenuPort updateFoodMenuPort;

    public SchedulePendingChangesUseCaseImpl(LoadFoodMenuPort loadFoodMenuPort, UpdateFoodMenuPort updateFoodMenuPort) {
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPort = updateFoodMenuPort;
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
