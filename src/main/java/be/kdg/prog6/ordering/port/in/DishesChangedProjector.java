package be.kdg.prog6.ordering.port.in;

import be.kdg.prog6.common.events.DishMarkedOutOfStockEvent;
import be.kdg.prog6.common.events.DishPublishedToMenuEvent;
import be.kdg.prog6.common.events.DishUnpublishedToMenuEvent;

public interface DishesChangedProjector {
    void project(DishPublishedCommand command);
    void project(DishUnpublishedCommand command);
    void project(DishMarkedOutOfStockCommand command);
}
