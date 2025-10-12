package be.kdg.prog6.ordering.adaptor.in;

import be.kdg.prog6.common.events.DishPublishedToMenuEvent;
import be.kdg.prog6.common.events.DishUnpublishedToMenuEvent;
import be.kdg.prog6.ordering.port.in.DishesChangedProjector;
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
        projector.project(event);
    }

    @EventListener(DishUnpublishedToMenuEvent.class)
    public void onDishUnpublished(DishUnpublishedToMenuEvent event) {
        logger.info("Dish Unpublished received");
        projector.project(event);
    }
}