package be.kdg.prog6.restaurant.core;

import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.domain.vo.dish.DishVersion;
import be.kdg.prog6.restaurant.port.in.EditDishCommand;
import be.kdg.prog6.restaurant.port.in.EditDishUseCase;
import be.kdg.prog6.restaurant.port.out.LoadDishPort;
import be.kdg.prog6.restaurant.port.out.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.UpdateFoodMenuPort;
import org.springframework.stereotype.Service;

@Service
public class EditDishUseCaseImpl implements EditDishUseCase {

    private final LoadDishPort loadDishPort;
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final UpdateFoodMenuPort updateFoodMenuPort;

    public EditDishUseCaseImpl(LoadDishPort loadDishPort, LoadFoodMenuPort loadFoodMenuPort, UpdateFoodMenuPort updateFoodMenuPort) {
        this.loadDishPort = loadDishPort;
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPort = updateFoodMenuPort;
    }

    @Override
    public Dish editDish(EditDishCommand command) {

        Dish dish = loadDishPort.loadDish(command.dishId())
                .orElseThrow(() -> new IllegalArgumentException("Dish not found with id: " + command.dishId()));

        FoodMenu foodMenu = loadFoodMenuPort.loadBy(command.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("FoodMenu not found for restaurant: " + command.restaurantId()));

        DishVersion draft = new DishVersion(
                command.name(),
                command.description(),
                command.price(),
                command.pictureUrl(),
                command.tags(),
                command.dishType()
        );

        dish.editDraft(draft);
        foodMenu.updateDish(dish);

        updateFoodMenuPort.updateFoodMenu(foodMenu);
        return dish;
    }
}