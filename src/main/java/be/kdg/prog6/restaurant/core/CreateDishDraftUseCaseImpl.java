package be.kdg.prog6.restaurant.core;

import be.kdg.prog6.restaurant.domain.Dish;
import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.domain.vo.dish.DishVersion;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.port.in.CreateDishDraftCommand;
import be.kdg.prog6.restaurant.port.in.CreateDishDraftUseCase;
import be.kdg.prog6.restaurant.port.out.LoadRestaurantPort;
import be.kdg.prog6.restaurant.port.out.UpdateDishPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateDishDraftUseCaseImpl implements CreateDishDraftUseCase {

    private final Logger logger = LoggerFactory.getLogger(CreateDishDraftUseCaseImpl.class.getName());

    private final UpdateDishPort updateDishPort;
    private final LoadRestaurantPort loadRestaurantPort;

    public CreateDishDraftUseCaseImpl(UpdateDishPort updateDishPort, LoadRestaurantPort loadRestaurantPort) {
        this.updateDishPort = updateDishPort;
        this.loadRestaurantPort = loadRestaurantPort;
    }

    @Override
    @Transactional
    public Dish createDishDraftForFoodMenu(CreateDishDraftCommand command) {
        RestaurantId restaurantId = command.restaurantId();
        if (loadRestaurantPort.findById(restaurantId).isEmpty()) {
            logger.info("restaurant does not exist");
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

        this.updateDishPort.addDish(dish,restaurantId.id());
        return dish;
    }
}
