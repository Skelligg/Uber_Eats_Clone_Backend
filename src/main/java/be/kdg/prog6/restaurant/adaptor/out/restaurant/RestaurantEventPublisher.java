package be.kdg.prog6.restaurant.adaptor.out.restaurant;

import be.kdg.prog6.restaurant.domain.Restaurant;
import be.kdg.prog6.restaurant.port.out.restaurant.UpdateRestaurantPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEventPublisher implements UpdateRestaurantPort {

    private final ApplicationEventPublisher applicationEventPublisher;

    public RestaurantEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        restaurant.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        restaurant.clearDomainEvents();
        return restaurant;
    }


}
