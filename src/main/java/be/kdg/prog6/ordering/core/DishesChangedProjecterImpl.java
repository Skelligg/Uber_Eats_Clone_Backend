package be.kdg.prog6.ordering.core;

import be.kdg.prog6.common.events.DishCreatedEvent;
import be.kdg.prog6.ordering.domain.projection.DishProjection;
import be.kdg.prog6.ordering.port.in.DishesChangedProjector;
import be.kdg.prog6.ordering.port.out.UpdateDishesPort;
import org.springframework.stereotype.Service;

@Service
public class DishesChangedProjecterImpl implements DishesChangedProjector {
        private final UpdateDishesPort updateDishesPort;

    public DishesChangedProjecterImpl(UpdateDishesPort updateDishesPort) {
        this.updateDishesPort = updateDishesPort;
    }

    @Override
    public void project(DishCreatedEvent event) {
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
}
