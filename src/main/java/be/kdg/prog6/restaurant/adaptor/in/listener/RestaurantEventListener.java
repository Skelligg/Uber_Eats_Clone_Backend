package be.kdg.prog6.restaurant.adaptor.in.listener;

import be.kdg.prog6.common.events.restaurant.RestaurantCreatedEvent;
import be.kdg.prog6.restaurant.domain.vo.restaurant.RestaurantId;
import be.kdg.prog6.restaurant.port.in.foodmenu.CreateFoodMenuUseCase;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RestaurantEventListener {
    private final CreateFoodMenuUseCase createFoodMenuUseCase;

    public RestaurantEventListener(CreateFoodMenuUseCase createFoodMenuUseCase) {
        this.createFoodMenuUseCase = createFoodMenuUseCase;
    }

    @EventListener
    public void onRestaurantCreated(RestaurantCreatedEvent event) {
        createFoodMenuUseCase.createMenuForRestaurant(new RestaurantId(UUID.fromString(event.restaurantId())));
    }
}
