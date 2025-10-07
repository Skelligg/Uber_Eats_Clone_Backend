package be.kdg.prog6.ordering.port.in;

import be.kdg.prog6.common.events.DishCreatedEvent;

public interface DishesChangedProjector {
    void project(DishCreatedEvent event);
}
