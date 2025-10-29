package be.kdg.prog6.restaurant.core.dish;

import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.domain.vo.dish.DishVersion;
import be.kdg.prog6.restaurant.port.in.dish.EditDishCommand;
import be.kdg.prog6.restaurant.port.in.dish.EditDishUseCase;
import be.kdg.prog6.restaurant.port.out.dish.LoadDishPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.UpdateFoodMenuPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditDishUseCaseImpl implements EditDishUseCase {

    private final LoadDishPort loadDishPort;
    private final LoadFoodMenuPort loadFoodMenuPort;
    private final List<UpdateFoodMenuPort> updateFoodMenuPorts;

    public EditDishUseCaseImpl(LoadDishPort loadDishPort, LoadFoodMenuPort loadFoodMenuPort, List<UpdateFoodMenuPort> updateFoodMenuPorts) {
        this.loadDishPort = loadDishPort;
        this.loadFoodMenuPort = loadFoodMenuPort;
        this.updateFoodMenuPorts = updateFoodMenuPorts;
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

        this.updateFoodMenuPorts.forEach(port -> port.updateFoodMenu(foodMenu));
        return dish;
    }
}