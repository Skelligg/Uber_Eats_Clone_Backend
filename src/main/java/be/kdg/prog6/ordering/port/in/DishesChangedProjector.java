package be.kdg.prog6.ordering.port.in;

import be.kdg.prog6.common.events.DishPublishedToMenuEvent;
import be.kdg.prog6.common.events.DishUnpublishedToMenuEvent;

public interface DishesChangedProjector {
    void project(DishPublishedToMenuEvent event);
    void project(DishUnpublishedToMenuEvent event);

}
