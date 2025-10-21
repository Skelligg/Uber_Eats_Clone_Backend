package be.kdg.prog6.ordering.adaptor.in;

import be.kdg.prog6.common.events.RestaurantCreatedEvent;
import be.kdg.prog6.ordering.port.in.restaurant.RestaurantAddedProjectionCommand;
import be.kdg.prog6.ordering.port.in.restaurant.RestaurantsChangedProjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RestaurantsChangedListener {
    private final Logger logger = LoggerFactory.getLogger(RestaurantsChangedListener.class);
    private final RestaurantsChangedProjector projector;

    public RestaurantsChangedListener(RestaurantsChangedProjector projector) {
        this.projector = projector;
    }

    @EventListener(RestaurantCreatedEvent.class)
    public void restaurantsChanged(RestaurantCreatedEvent restaurantCreatedEvent) {
        projector.project(RestaurantAddedProjectionCommand.fromEvent(restaurantCreatedEvent));
    }
}
