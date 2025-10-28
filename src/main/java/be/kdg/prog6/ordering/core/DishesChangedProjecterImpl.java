package be.kdg.prog6.ordering.core;

import be.kdg.prog6.common.vo.DISH_TYPE;
import be.kdg.prog6.ordering.domain.projection.DISH_AVAILABILITY;
import be.kdg.prog6.ordering.domain.projection.DishProjection;
import be.kdg.prog6.ordering.port.in.dish.*;
import be.kdg.prog6.ordering.port.out.dish.LoadDishesPort;
import be.kdg.prog6.ordering.port.out.dish.UpdateDishesPort;
import org.springframework.stereotype.Service;

@Service
public class DishesChangedProjecterImpl implements DishesChangedProjector {
        private final UpdateDishesPort updateDishesPort;
        private final LoadDishesPort loadDishesPort;

    public DishesChangedProjecterImpl(UpdateDishesPort updateDishesPort, LoadDishesPort loadDishesPort) {
        this.updateDishesPort = updateDishesPort;
        this.loadDishesPort = loadDishesPort;
    }

    @Override
    public void project(DishPublishedCommand command) {
        DishProjection projection = new DishProjection(
                command.dishId(),
                command.foodMenuId(),
                command.name(),
                command.description(),
                command.price(),
                command.pictureUrl(),
                command.tags(),
                DISH_TYPE.valueOf(command.dishType().toUpperCase()),
                DISH_AVAILABILITY.AVAILABLE
        );

        updateDishesPort.updateDishes(projection);
    }

    @Override
    public void project(DishUnpublishedCommand command) {
        var projection = loadDishesPort.loadDish(command.dishId());
        if (projection.isEmpty()) {
            return;
        }
        updateDishesPort.removeDish(projection.get());
    }

    @Override
    public void project(DishMarkedOutOfStockCommand command) {
        var projection = loadDishesPort.loadDish(command.dishId());
        if (projection.isEmpty()) {
            return;
        }
        projection.get().setDishState(DISH_AVAILABILITY.OUT_OF_STOCK);
        updateDishesPort.updateDishes(projection.get());
    }

    @Override
    public void project(DishMarkedAvailableCommand command) {
        var projection = loadDishesPort.loadDish(command.dishId());
        if (projection.isEmpty()) {
            return;
        }
        projection.get().setDishState(DISH_AVAILABILITY.AVAILABLE);
        updateDishesPort.updateDishes(projection.get());
    }
}
