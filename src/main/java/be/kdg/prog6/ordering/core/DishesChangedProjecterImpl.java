package be.kdg.prog6.ordering.core;

import be.kdg.prog6.common.events.DishPublishedToMenuEvent;
import be.kdg.prog6.common.events.DishUnpublishedToMenuEvent;
import be.kdg.prog6.ordering.domain.projection.DishProjection;
import be.kdg.prog6.ordering.port.in.DishesChangedProjector;
import be.kdg.prog6.ordering.port.out.LoadDishesPort;
import be.kdg.prog6.ordering.port.out.UpdateDishesPort;
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
    public void project(DishPublishedToMenuEvent event) {
        DishProjection projection = new DishProjection(
                event.dishId(),
                event.foodMenuId(),
                event.name(),
                event.description(),
                event.price(),
                event.pictureUrl(),
                event.tags(),
                event.dishType()
        );

        updateDishesPort.updateDishes(projection);
    }

    @Override
    public void project(DishUnpublishedToMenuEvent event) {
        var projection = loadDishesPort.loadDish(event.dishId());
        if (projection.isEmpty()) {
            return;
        }
        updateDishesPort.removeDish(projection.get());
    }
}
