package be.kdg.prog6.ordering.adaptor.in;

import be.kdg.prog6.common.events.*;
import be.kdg.prog6.ordering.port.in.dish.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DishesChangedListener {
    private final DishesChangedProjector projector;
    private final Logger logger = LoggerFactory.getLogger(DishesChangedListener.class);

    public DishesChangedListener(DishesChangedProjector projector) {
        this.projector = projector;
    }

    @EventListener(DishPublishedToMenuEvent.class)
    public void onDishPublished(DishPublishedToMenuEvent event) {
        logger.info("DishCreatedEvent received");
        projector.project(new DishPublishedCommand(
                event.dishId(),
                event.foodMenuId(),
                event.name(),
                event.description(),
                event.price(),
                event.pictureUrl(),
                event.tags(),
                event.dishType()
        ));
    }

    @EventListener(DishUnpublishedToMenuEvent.class)
    public void onDishUnpublished(DishUnpublishedToMenuEvent event) {
        projector.project(new DishUnpublishedCommand(event.dishId()));
    }

    @EventListener(DishMarkedOutOfStockEvent.class)
    public void onDishMarkedOutOfStock(DishMarkedOutOfStockEvent event) {
        projector.project(new DishMarkedOutOfStockCommand(event.dishId()));
    }

    @EventListener(DishMarkedAvailableEvent.class)
    public void onDishMarkedAvailable(DishMarkedAvailableEvent event) {
        projector.project(new DishMarkedAvailableCommand(event.dishId()));
    }
}