// ordering/core/FoodMenusChangedProjecterImpl.java
package be.kdg.prog6.ordering.core;

import be.kdg.prog6.common.events.foodmenu.FoodMenuCreatedEvent;
import be.kdg.prog6.ordering.domain.projection.FoodMenuProjection;
import be.kdg.prog6.ordering.port.in.foodmenu.FoodMenusChangedProjector;
import be.kdg.prog6.ordering.port.out.foodmenu.UpdateFoodMenusPort;
import org.springframework.stereotype.Service;

@Service
public class FoodMenusChangedProjecterImpl implements FoodMenusChangedProjector {
    private final UpdateFoodMenusPort updateFoodMenusPort;

    public FoodMenusChangedProjecterImpl(UpdateFoodMenusPort updateFoodMenusPort) {
        this.updateFoodMenusPort = updateFoodMenusPort;
    }

    @Override
    public void project(FoodMenuCreatedEvent event) {
        FoodMenuProjection projection = new FoodMenuProjection(
                event.restaurantId(),
                event.averageMenuPrice()
        );
        updateFoodMenusPort.updateFoodMenus(projection);
    }
}
