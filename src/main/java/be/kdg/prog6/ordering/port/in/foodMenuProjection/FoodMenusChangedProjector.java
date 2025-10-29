package be.kdg.prog6.ordering.port.in.foodMenuProjection;

import be.kdg.prog6.common.events.foodmenu.FoodMenuCreatedEvent;

public interface FoodMenusChangedProjector {
    void project(FoodMenuCreatedEvent event);
}
