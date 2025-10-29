package be.kdg.prog6.restaurant.adaptor.out.foodMenu;

import be.kdg.prog6.restaurant.domain.FoodMenu;
import be.kdg.prog6.restaurant.port.out.foodmenu.UpdateFoodMenuPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class FoodMenuEventPublisher implements UpdateFoodMenuPort {

    private final ApplicationEventPublisher applicationEventPublisher;

    public FoodMenuEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public FoodMenu updateFoodMenu(FoodMenu foodMenu) {
        foodMenu.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        foodMenu.clearDomainEvents();
        return foodMenu;
    }
}
