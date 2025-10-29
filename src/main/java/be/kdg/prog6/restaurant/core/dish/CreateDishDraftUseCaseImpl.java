package be.kdg.prog6.restaurant.core.dish;

import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.vo.dish.DishVersion;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.port.in.dish.CreateDishDraftCommand;
import be.kdg.prog6.restaurant.port.in.dish.CreateDishDraftUseCase;
import be.kdg.prog6.restaurant.port.out.foodmenu.LoadFoodMenuPort;
import be.kdg.prog6.restaurant.port.out.foodmenu.UpdateFoodMenuPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreateDishDraftUseCaseImpl implements CreateDishDraftUseCase {

    private final Logger logger = LoggerFactory.getLogger(CreateDishDraftUseCaseImpl.class.getName());

    private final List<UpdateFoodMenuPort> updateFoodMenuPorts;
    private final LoadFoodMenuPort loadFoodMenuPort;

    public CreateDishDraftUseCaseImpl(List<UpdateFoodMenuPort> updateFoodMenuPorts, LoadFoodMenuPort loadFoodMenuPort) {
        this.updateFoodMenuPorts = updateFoodMenuPorts;
         this.loadFoodMenuPort = loadFoodMenuPort;
    }

    @Override
    @Transactional
    public Dish createDishDraftForFoodMenu(CreateDishDraftCommand command) {
        RestaurantId restaurantId = command.restaurantId();
        var foodMenu = loadFoodMenuPort.loadBy(restaurantId);
        if (foodMenu.isEmpty()) {
            logger.info("Menu does not exist");
            return null;
        }

        Dish dish = new Dish(
                new DishVersion(
                        command.name(),
                        command.description(),
                        command.price(),
                        command.pictureUrl(),
                        command.tags(),
                        command.dishType()
                )
        );

        foodMenu.get().addDish(dish);

        this.updateFoodMenuPorts.forEach(port -> port.updateFoodMenu(foodMenu.get()));
        return dish;
    }
}
