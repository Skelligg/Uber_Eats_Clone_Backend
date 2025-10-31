package be.kdg.prog6.restaurant.core.dish;

import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.port.in.dish.ScheduleChangesCommand;
import be.kdg.prog6.restaurant.port.in.dish.SchedulePendingChangesUseCase;
import be.kdg.prog6.restaurant.port.out.foodmenu.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.UpdateFoodMenuPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchedulePendingChangesUseCaseImpl implements SchedulePendingChangesUseCase {
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final List<UpdateFoodMenuPort> updateFoodMenuPorts;

    public SchedulePendingChangesUseCaseImpl(LoadFoodMenuPort loadFoodMenuPort, List<UpdateFoodMenuPort> updateFoodMenuPorts) {
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPorts = updateFoodMenuPorts;
    }

    @Override
    @Transactional
    public FoodMenu scheduleChanges(ScheduleChangesCommand command) {
        FoodMenu foodMenu = loadFoodMenuPort.loadBy(command.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("FoodMenu not found for restaurant: " + command.restaurantId()));

        foodMenu.scheduleDishes(command.dishIds(),command.publicationTime(),command.stateToBecome());

        this.updateFoodMenuPorts.forEach(updateFoodMenuPort -> updateFoodMenuPort.updateFoodMenu(foodMenu));

        return foodMenu;
    }

}
