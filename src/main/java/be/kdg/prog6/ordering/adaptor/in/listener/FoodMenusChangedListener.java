// ordering/adaptor/in/FoodMenusChangedListener.java
package be.kdg.prog6.ordering.adaptor.in.listener;

import be.kdg.prog6.common.events.foodmenu.FoodMenuCreatedEvent;
import be.kdg.prog6.ordering.port.in.foodMenuProjection.FoodMenusChangedProjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FoodMenusChangedListener {
    private final FoodMenusChangedProjector projector;
    private final Logger logger = LoggerFactory.getLogger(FoodMenusChangedListener.class);

    public FoodMenusChangedListener(FoodMenusChangedProjector projector) {
        this.projector = projector;
    }

    @EventListener(FoodMenuCreatedEvent.class)
    public void onFoodMenuCreated(FoodMenuCreatedEvent event) {
        logger.info("FoodMenuChangedEvent received");
        projector.project(event);
    }
}
