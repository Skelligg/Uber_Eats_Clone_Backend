// ordering/port/in/FoodMenusChangedProjector.java
package be.kdg.prog6.ordering.port.in;

import be.kdg.prog6.common.events.FoodMenuCreatedEvent;

public interface FoodMenusChangedProjector {
    void project(FoodMenuCreatedEvent event);
}
